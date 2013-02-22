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


from time import sleep

import os
import re
import sys

import ConfigParser

def get_credentials(env):
    solenopsis_properties_file = open(os.path.expanduser('~/solenopsis.properties'),'r')
    credentials_file_location = False
    for line in solenopsis_properties_file:
        if not line.strip():
            continue
        (name,val) = re.split(r'\s*=\s*', line.rstrip('\n'))
        if name == 'solenopsis.env.HOME':
            credentials_file_location = '%s/credentials/%s.properties' % (val,env)
            break
    solenopsis_properties_file.close()

    if not credentials_file_location or not os.path.exists(credentials_file_location):
        print "Cannot find credentials file location: %s" % (credentials_file_location,)
        sys.exit(0)

    credentials_file = open(credentials_file_location,'r')
    credentials_hash = {}

    for line in credentials_file:
        if not line.strip():
            continue
        (name,val) = re.split(r'\s*=\s*', line.rstrip('\n'))
        if name == 'username':
            credentials_hash['username'] = val
        if name == 'password':
            credentials_hash['pw'] = val

    credentials_file.close()

    if not credentials_hash or not credentials_hash.has_key('username') or not credentials_hash.has_key('pw'):
        print "Cannot determine credentials from: %s" % (credentials_file_location,)

    return credentials_hash

def get_sleep(type):
    sleep_hash = {
        'short' : 3,
        'long'  : 30,
    }
    return sleep_hash.get(type, 10)

def short_sleep():
    sleep(get_sleep('short'))

def long_sleep():
    sleep(get_sleep('long'))

def impersonate_bugzilla_automation_user(browser,debug):
    for link_name in ['Setup', 'Manage Users', 'Users', 'Automation, Bugzilla']:
        if debug:
            print "Clicking on %s" % (link_name,)
        elem = browser.find_element_by_link_text(link_name)
        elem.click()

        short_sleep()

    elem = browser.find_element_by_name('login')
    elem.click()