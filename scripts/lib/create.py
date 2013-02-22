#!/usr/bin/python

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


"""This class is designed to take templates and write them out to disk
"""

__author__ = "Patrick Connelly (patrick@deadlypenguin.com)"
__version__ = "1.2"

import os
import sys
import logger

from string import Template

ABSOLUTE = False
FORCE = False
SRC_DIR = None
API_VERSION = 24    # Setting a logical default
TEMPLATE_DIR = "/usr/share/solenopsis/scripts/templates"

TEMPLATE_TXT = {
    "page": "page.template",
    "class": "class.template",
    "trigger": "trigger.template",
    "test": "class-test.template",
    "webservice": "webservice.template",
    "class-trigger": "class-trigger.template"
}

TEMPLATE_XML = {
    "page": "page.xml",
    "class": "class.xml",
    "trigger": "trigger.xml",
    "test": "class.xml",
    "webservice": "class.xml",
    "class-trigger": "class.xml"
}

TEMPLATE_EXT = {
    "page": "page",
    "class": "cls",
    "trigger": "trigger",
    "test": "cls",
    "webservice": "cls",
    "class-trigger": "cls"
}

def setTemplateDir(path):
    """Sets the template path

    path - The new template path
    """
    global TEMPLATE_DIR
    TEMPLATE_DIR=path

def getTemplateDir():
    """Gets the template path"""
    return TEMPLATE_DIR

def setApiVersion(ver):
    """Sets the api version to use

    ver - The api version
    """
    global API_VERSION
    API_VERSION = ver

def getApiVersion():
    """Gets the api version"""
    return API_VERSION

def setRelativity(status):
    """Sets if the relativity of the creation

    status - True / False if it is relative
    """
    global ABSOLUTE
    ABSOLUTE = status

def isRelative():
    """Returns if it is relative or not"""
    return not ABSOLUTE

def setForce(status):
    """Sets if we should force overwrite files

    status - True / False if force is on
    """
    global FORCE
    FORCE = status

def isForced():
    """Returns if force overwrite is on"""
    return FORCE

def setSourceDir(path):
    """Sets the source dir

    path - The source dir
    """
    global SRC_DIR
    SRC_DIR = path

def getSourceDir():
    """Gets the source dir"""
    return SRC_DIR

def generateFile(fname, params):
    """Returns the generated file body

    fname - The file name to write to
    params - The params past in
            [1] label/name
            [2] object name
    """
    file_path = '%s/%s' % (getTemplateDir(), fname,)
    try:
        f = open(file_path, 'r')
    except:
        logger.critical('Unable to open %s' % (file_path,))
        sys.exit(-1)

    template = Template(f.read())

    f.close()

    body = template.safe_substitute(name=params[1], label=params[1],
                    api_version=getApiVersion())

    if (len(params) == 3):
        template = Template(body)
        body = template.safe_substitute(object=params[2])

    return body

def writeTemplate(body, dest):
    """Writes the template to disk

    body - The body to write
    dest - The file destination to write
    """
    if os.path.exists(dest) and not isForced():
        logger.critical('Destination exists "%s" skipping...' % (dest,))
        sys.exit(-1)

    try:
        f = open(dest, 'w')
        f.write(body)
        f.close()
    except:
        logger.crititcal('Unable to open %s for writing' % (dest,))
        sys.exit(-1)

def createFile(params):
    """Creates the file from a tmplate

    params - The paramaters
        [0] type
        [n] See generate file
    """
    if params[0] == 'class' and len(params) != 2:
        logger.critical('Invalid parameters.\n Usage: create class NAME')
        sys.exit(-1)
    elif params[0] == 'trigger' and len(params) != 3:
        if len(params) == 2:
            logger.warning('No object name given.  Using generic name')
            params.append('MyObject__c')
        else:
            logger.critical('Invalid parameters.\n Usage: create trigger NAME OBJECT')
            sys.exit(-1)

    template = None
    try:
        template = TEMPLATE_TXT[params[0]]
    except KeyError, (errno, strerror):
        logger.critical('Unable to find template for %s' % (strerror,))
        sys.exit(-1)

    extension = None
    try:
        extension = TEMPLATE_EXT[params[0]]
    except KeyError, (errno, strerror):
        logger.critical('Unable to find extension for %s' % (strerror,))
        sys.exit(-1)

    xml = None
    try:
        xml = TEMPLATE_XML[params[0]]
    except KeyError, (errno, strerror):
        logger.critical('Unable to find template for %s' % (strerror,))
        sys.exit(-1)

    template_dest = None

    if not isRelative():
        if not getSourceDir() or getSourceDir().__len__ == 0:
            logger.critical('Absolute flag set, but source dir not specified')
            sys.exit(-1)
        if getSourceDir()[-1:] == "/":
            template_dest = getSourceDir()[0:-1]
        else:
            template_dest = getSourceDir()
    else:
        template_dest = os.getcwd()

    file_name = None

    try:
        file_name = params[1]
    except IndexError:
        logger.debug('Got an IndexError.  Not enough params')

    if not file_name or file_name.__len__ == 0:
        logger.critical('No name given')
        sys.exit(-1)

    template_dest = '%s/%s.%s' % (template_dest, file_name, extension,)
    xml_dest = '%s%s' % (template_dest, '-meta.xml',)

    template_body = generateFile(template, params)
    xml_body = generateFile(xml, params)

    writeTemplate(template_body, template_dest)
    writeTemplate(xml_body, xml_dest)