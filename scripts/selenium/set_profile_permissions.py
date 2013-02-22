# Copyright 2011 Red Hat Inc.
#
# This file is part of solenopsis
#
# solenopsis is free software; you can redistribute it and/or
# modify it under the terms of the GNU General Public License
# as published by the Free Software Foundation; either version 3
# of the License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA

import getopt
import os
import sys
import csv
import urllib
import re

from selenium import webdriver
from time import sleep

from datetime import datetime, date, time

from utils import *

try:
    opts, args = getopt.getopt(sys.argv[1:], "", ['debug', 'env=', 'profile=','object=','rights=','classes=','vf=',])
except:
    print str(err)


debug = False
env = ''
to_fix = [] 

objectLabel = False
profile_array = []
right_array = []
class_array = []
vf_array = []

allowable_rights_hash = {
    'read'          : 'Read',
    'create'        : 'Create',
    'edit'          : 'Edit',
    'delete'        : 'Delete',
    'viewall'       : 'Read All',
    'modifyall'     : 'Manage All',
}

all_allowable_rights_hash = {
    'readall'       : 'Read All',
    'manageall'     : 'Manage All',
}
all_allowable_rights_hash.update(allowable_rights_hash)

allowable_profile_hash = {
    'manager'                           : 'Manager',
    'namedresource'                     : 'Named Resource Profile',
    'named_resource'                    : 'Named Resource Profile',
    'named_resource_profile'            : 'Named Resource Profile',
    'partnermanager'                    : 'Partner Manager',
    'partner_manager'                   : 'Partner Manager',
    'qa'                                : 'QA',
    'systemadministrator'               : 'System Administrator',
    'system_administrator'              : 'System Administrator',
    'tseprofile'                        : 'TSE Profile',
    'tse_profile'                       : 'TSE Profile',
    'tse'                               : 'TSE Profile',
    'vp+'                               : 'VP+',
    'vp'                                : 'VP+',
    'read_only_gss'                     : 'Read Only (GSS)',
    'readonly_gss'                      : 'Read Only (GSS)',
    'gss_read_only'                     : 'Read Only (GSS)',
    'gss_readonly'                      : 'Read Only (GSS)',
}

for o, a in opts:
    if o in ('--debug'):
        debug = True
    if o in ('--env'):
        env = a.lower()
    if o in ('--profile'):
        a = a.lower()
        profile_array = [val.lower() for val in a.split(',') if allowable_profile_hash.has_key(val)]
        invalid_profiles = [val.lower() for val in a.split(',') if not allowable_profile_hash.has_key(val)]
        if invalid_profiles:
            print "Invalid Profiles passed: %s" % (','.join(invalid_profiles),)
            sys.exit(0)
    if o in ('--object'):
        objectLabel = a
    if o in ('--rights'):
        a = a.lower()
        if a.lower() == 'all':
            right_array = ['viewall','modifyall','create',]
        else:
            right_array = [val.lower() for val in a.split(',') if all_allowable_rights_hash.has_key(val)]
            invalid_rights = [val.lower() for val in a.split(',') if not all_allowable_rights_hash.has_key(val)]
            if invalid_rights:
                print "Invalid Rights requested: %s" % (','.join(invalid_rights),)
                sys.exit(0)
    if o in ('--classes'):
        class_array = a.split(',')
    if o in ('--vf'):
        vf_array = a.split(',')

if not profile_array:
    print "Need to specify --profile"
    sys.exit(0)

if not profile_array and not right_array and not class_array and not vf_array:
    print "Need to specify --profile --rights --classes or --vf"
    sys.exit(0)

if right_array and not objectLabel:
    print "Need to specify --object if setting rights"
    sys.exit(0)

login_info = get_credentials(env)

initial_login_url = 'https://test.salesforce.com'
if env == 'prod':
    initial_login_url = 'https://login.salesforce.com'

if debug:
    print "Starting Firefox"

browser = webdriver.Firefox()
try:
    browser.get(initial_login_url)
    short_sleep()

    for key in login_info:
        elem = browser.find_element_by_name(key)
        elem.send_keys(login_info[key])

    elem = browser.find_element_by_name('Login')
    elem.click()

    short_sleep()

    base_sfdc_url = browser.current_url
    base_sfdc_url = re.sub(r'home/home.jsp', r'%s', base_sfdc_url) 

    for profile in profile_array:
        profile_name = allowable_profile_hash[profile]
        if debug:
            print "Setting profile: %s" % (profile_name,)

        if debug:
            print "Looking for Setup link"

        elem = browser.find_element_by_link_text('Setup')
        elem.click()

        try:
            if debug:
                print "Looking for Profiles link"
            profiles_elem = browser.find_element_by_link_text('Profiles')
        except:
            if debug:
                print "Profiles Link not found... Looking to expand Manage Users"
            try:
                users_elem = browser.find_element_by_link_text('Manage Users') 
            except:
                print "Cannot find Manage Users"
                sys.exit(0)
            users_elem.click()
            short_sleep()
            if debug:
                print "Looking for Profiles Link"
            profiles_elem = browser.find_element_by_link_text('Profiles')
        if profiles_elem:
            profiles_elem.click()

        short_sleep()

        if debug:
            print "Looking for profile: %s" % (profile_name,)
        individual_profile_elem = browser.find_element_by_link_text(profile_name)
        individual_profile_elem.click()

        short_sleep()

        if class_array:
            if debug:
                print "Viewing %s's Enabled Apex Classes" % (profile_name,)
            elem = browser.find_element_by_xpath('//input[contains(@onclick,"/_ui/system/user/ProfileApexClassPermissionEdit/")]')
            elem.click() 

            short_sleep()

            for class_name in class_array:
                try:
                    elem = browser.find_element_by_xpath('//select[@id="duel_select_0"]/option[. = "%s"]' % (class_name,))
                    elem.select()
                    elem = browser.find_element_by_xpath('//img[@title="Add"]')
                    elem.click()
                    short_sleep()
                except:
                    pass

            elem = browser.find_element_by_name('save')
            elem.click()

            short_sleep()

        if vf_array:
            if debug:
                print "Viewing %s's Enabled VF Pages" % (profile_name,)
            elem = browser.find_element_by_xpath('//input[contains(@onclick,"/_ui/system/user/ProfileApexPagePermissionEdit/")]')
            elem.click() 

            short_sleep()

            for vf_name in vf_array:
                try:
                    elem = browser.find_element_by_xpath('//select[@id="duel_select_0"]/option[. = "%s"]' % (vf_name,))
                    elem.select()
                    elem = browser.find_element_by_xpath('//img[@title="Add"]')
                    elem.click()
                except:
                    pass
                short_sleep()
            
            elem = browser.find_element_by_name('save')
            elem.click()

            short_sleep()

        if right_array:
            if debug:
                print "Clicking Edit button"
            elem = browser.find_element_by_name('edit')
            elem.click()
            
            short_sleep()

            if profile_name != 'System Administrator':
                for right in allowable_rights_hash.keys():
                    if debug:
                        print "Cleaning up Profile %s for object %s removing the permission to %s" % (profile_name, objectLabel, allowable_rights_hash[right],)
                    elem = browser.find_element_by_xpath('//input[@title="%s %ss"]' % (allowable_rights_hash[right], objectLabel,))
                    if elem.is_selected():
                        elem.click()
                    short_sleep()

                for right in right_array:
                    if debug:
                        print "Giving Profile %s the permission to %s" % (objectLabel, allowable_rights_hash[right],)
                    elem = browser.find_element_by_xpath('//input[@title="%s %ss"]' % (allowable_rights_hash[right], objectLabel,))
                    elem.click() 
                    short_sleep()
                
                elem = browser.find_element_by_name('save')
                elem.click()

            short_sleep()

finally:
    browser.close()
    