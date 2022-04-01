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
from string import Template

from . import logger

ABSOLUTE = False
CREATE_FORCE = False
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
    global TEMPLATE_DIR # pylint: disable=global-statement
    TEMPLATE_DIR=path

def getTemplateDir():
    """Gets the template path"""
    return TEMPLATE_DIR

def setApiVersion(ver):
    """Sets the api version to use

    ver - The api version
    """
    global API_VERSION # pylint: disable=global-statement
    API_VERSION = ver

def getApiVersion():
    """Gets the api version"""
    return API_VERSION

def setRelativity(status):
    """Sets if the relativity of the creation

    status - True / False if it is relative
    """
    global ABSOLUTE # pylint: disable=global-statement
    ABSOLUTE = status

def isRelative():
    """Returns if it is relative or not"""
    return not ABSOLUTE

def setForce(status):
    """Sets if we should force overwrite files

    status - True / False if force is on
    """
    global CREATE_FORCE # pylint: disable=global-statement
    CREATE_FORCE = status

def isForced():
    """Returns if force overwrite is on"""
    return CREATE_FORCE

def setSourceDir(path):
    """Sets the source dir

    path - The source dir
    """
    global SRC_DIR # pylint: disable=global-statement
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
    filePath = '%s/%s' % (getTemplateDir(), fname,)
    template = None

    try:
        with open(filePath, 'r', encoding="utf-8") as file:
            template = Template(file.read())
    except: # pylint: disable=bare-except
        logger.critical('Unable to open %s' % (filePath,))
        sys.exit(-1)

    body = template.safe_substitute(name=params[1], label=params[1],
                    api_version=getApiVersion())

    if len(params) == 3:
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
        with open(dest, 'w', encoding="utf-8") as file:
            file.write(body)
    except: # pylint: disable=bare-except
        logger.critical('Unable to open %s for writing' % (dest,))
        sys.exit(-1)

def getFromMap(fileType, valueMap, name):
    """Gets the type from the map

    fileType - The type of the file
    m - The key / value map
    name - The type name"""
    value = None

    try:
        value = valueMap[fileType]
    except KeyError as error:
        logger.critical('Unable to find %s for %s' % (name, error,))
        sys.exit(-1)

    return value

def getTemplate(fileType):
    """Gets the template path

    fileType - The type of the file"""
    return getFromMap(fileType, TEMPLATE_TXT, 'template')

def getExtension(fileType):
    """Gets the file extension

    fileType - The type of the file"""
    return getFromMap(fileType, TEMPLATE_TXT, 'extension')

def getXML(fileType):
    """Gets the XML path

    fileType - The type of the file"""
    return getFromMap(fileType, TEMPLATE_XML, 'xml')

def getTemplateDest():
    """Gets the template destination"""
    templateDest = None

    if not isRelative():
        if not getSourceDir() or getSourceDir().__len__ == 0:
            logger.critical('Absolute flag set, but source dir not specified')
            sys.exit(-1)
        if getSourceDir()[-1:] == "/":
            templateDest = getSourceDir()[0:-1]
        else:
            templateDest = getSourceDir()
    else:
        templateDest = os.getcwd()

    return templateDest

def createFile(params):
    """Creates the file from a template

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

    fileType = params[0]
    template = getTemplate(fileType)
    extension = getExtension(fileType)
    xml = getXML(fileType)
    templateDest = getTemplateDest()
    fileName = params[1]

    templateDest = '%s/%s.%s' % (templateDest, fileName, extension,)
    xmlDest = '%s%s' % (templateDest, '-meta.xml',)

    templateBody = generateFile(template, params)
    xmlBody = generateFile(xml, params)

    writeTemplate(templateBody, templateDest)
    writeTemplate(xmlBody, xmlDest)
