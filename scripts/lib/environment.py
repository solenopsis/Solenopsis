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

"""This is used to generate new environment files as well as a new solenopsis.properties file"""

import os
import sys
import getpass

from . import logger
from . import parser
from . import osutils

HOME = '~/.solenopsis/'
FORCE = False
MASTER = None
DEPENDENT = None
DEFAULT_CONFIG = '~/solenopsis.properties'
DEFAULT_ENV = '~/.solenopsis/environments/'

USERNAME = None
PASSWORD = None
TOKEN = None

CONFIG_NAMES = {
    "env": "solenopsis.ENVIRONMENTS",
    "home": "solenopsis.env.HOME",
    "master": "solenopsis.env.MASTER",
    "dependent": "solenopsis.env.DEPENDENT",
    "local-home": "solenopsis.env.local.HOME"
}

CREDENTIAL_NAMES = {
    "username": "username",
    "password": "password",
    "token": "token",
    "url": "url"
}

URL_MAP = {
    "prod": "https://login.salesforce.com",
    "dev": "https://test.salesforce.com"
}

URL = URL_MAP["dev"]

RAW_CONFIG = {}

class FakeSecHead(): # pylint: disable=too-few-public-methods
    """ This is a horrible hack but since ConfigParser doesn't handle .properties
        files"""
    def __init__(self, fp): # pylint: disable=invalid-name
        """When we init the class create a file pointer and a fake section header"""
        self.fp = fp # pylint: disable=invalid-name
        self.sechead = '[section]\n'
    def readline(self):
        """Read a single file line"""
        if self.sechead:
            try:
                return self.sechead
            finally:
                self.sechead = None
        else:
            return self.fp.readline()

def setDefaultConfig(path):
    """Set the default config path

    path - The path to the solenopsis configuration file"""
    global DEFAULT_CONFIG # pylint: disable=global-statement
    DEFAULT_CONFIG = path

def getDefaultConfig():
    """Get the default config path"""
    return DEFAULT_CONFIG

def setRawConfig(cfg):
    """Set the raw config information.  This is all sections from the file

    cfg - A dict of the key/value pars in the solenopsis config file"""
    global RAW_CONFIG # pylint: disable=global-statement
    RAW_CONFIG = cfg

def getRawConfig():
    """Get the raw config"""
    return RAW_CONFIG

def setHome(path):
    """Set the home for solenopsis credentials and environments

    path - The name """
    global HOME # pylint: disable=global-statement
    HOME = path

def getHome():
    """Gets the home"""
    return HOME

def setMaster(name):
    """Sets the name of the master environment

    name - The name of the environment"""
    global MASTER # pylint: disable=global-statement
    MASTER = name

def getMaster():
    """Gets the name of the master environment"""
    return MASTER

def setDependent(name):
    """Sets the name of the dependent environment

    name - The name of the environment"""
    global DEPENDENT # pylint: disable=global-statement
    DEPENDENT = name

def getDependent():
    """Gets the name of the dependent environment"""
    return DEPENDENT

def setForce(status):
    """Sets if we should force overwrite config files

    status - True/False"""
    global FORCE # pylint: disable=global-statement
    FORCE = status

def isForced():
    """Returns if we should force overwrite config files"""
    return FORCE

def setUsername(name):
    """Sets the username for the current credential file

    name - The username"""
    global USERNAME # pylint: disable=global-statement
    USERNAME = name

def getUsername():
    """Gets the username for the current credential file"""
    return USERNAME

def setPassword(password):
    """Sets the password for the current credential file

    password - The password"""
    global PASSWORD # pylint: disable=global-statement
    PASSWORD = password

def getPassword():
    """Gets the password for the current credential file"""
    return PASSWORD

def setToken(tok):
    """Sets the token for the current credential file

    tok - The token"""
    global TOKEN # pylint: disable=global-statement
    TOKEN = tok

def getToken():
    """Gets the token for the current credential file"""
    return TOKEN

def setUrl(url):
    """Sets the url for the current credential file

    url - The url"""
    global URL # pylint: disable=global-statement
    URL = url

def getUrl():
    """Gets the url for the current credential file"""
    return URL

def getConfigFile():
    """Gets the config file path"""
    return os.path.expanduser(getDefaultConfig())

def getPropertyFile(prop):
    """Gets the property file name

    prop - The property name"""
    return '%s.properties' % (prop,)

def getDependentFile():
    """Gets the dependent file path"""
    return os.path.expanduser(DEFAULT_ENV + getPropertyFile(getDependent()))

def newCreds(name, username, password, token, isProd):
    """Generates a new credentials file

    name - The name of the new credential file
    username - The usename
    password - The password
    token - The token
    isProd - (Y/N)"""
    if not getHome():
        logger.critical('Home not set')
        sys.exit(-1)

    newFilename = getPropertyFile(name)
    credPath = os.path.expanduser(os.path.join(getHome(), 'credentials', newFilename))

    isProduction = False

    if isProd.upper() == 'Y' or isProd.upper() == 'YES':
        isProduction = True

    if os.path.exists(credPath) and not isForced():
        logger.critical('Credential file already exists "%s"' % (credPath,))
        sys.exit(-1)

    url = URL_MAP["dev"]
    if isProduction:
        url = URL_MAP["prod"]

    try:
        with open(credPath, 'w', encoding="utf-8") as file:
            file.write("%s=%s\n" % (CREDENTIAL_NAMES["username"], username,))
            file.write("%s=%s\n" % (CREDENTIAL_NAMES["password"], password,))
            file.write("%s=%s\n" % (CREDENTIAL_NAMES["token"], token,))
            file.write("%s=%s\n" % (CREDENTIAL_NAMES["url"], url,))
    except Exception as error: # pylint: disable=broad-except
        logger.critical('An error occurred trying to write to "%s": %s' % (credPath, error))
        sys.exit(-1)

def getDependentCreds():
    """Gets the credentials for the dependent environment"""
    return getCreds(getDependent())

def getCreds(name):
    """Gets the credentials for a given name

    name - The environment name"""
    parseCreds(name)
    return {
        "username": getUsername(),
        "password": getPassword(),
        "token": getToken(),
        "url": getUrl()
    }

def getCredPath(name):
    """Gets the credential file path

    name - The environment name"""
    credFilename = getPropertyFile(name)
    return os.path.expanduser(os.path.join(getHome(), 'credentials', credFilename))

def parseCreds(name):
    """Reads the credentials file

    name - The environment name"""
    if not getHome():
        logger.critical('Home not set')
        sys.exit(-1)

    credPath = getCredPath(name)

    try:
        configParser = parser.getParser()
        with open(credPath, encoding="utf-8") as credFile:
            if osutils.isPython2():
                configParser.readfp(FakeSecHead(credFile)) # pylint: disable=deprecated-method
            else:
                configParser.read_file(FakeSecHead(credFile))
            setUsername(configParser.get('section', 'username'))
            setPassword(configParser.get('section', 'password'))
            setToken(configParser.get('section', 'token'))
            if configParser.has_option('section', 'url'):
                setUrl(configParser.get('section', 'url'))
    except: # pylint: disable=bare-except
        logger.critical('An error occurred trying to read "%s"' % (credPath))
        sys.exit(-1)

def newCredsInteractive():
    """Prompts interactively for new credentials"""
    name = input("Please enter your environment name: ")
    username = input("Please enter your salesforce username: ")
    password = getpass.getpass(prompt="Please enter your salesforce password: ")
    token = input("Please enter your salesforce token: ")
    isProd = input("Is this a production instance? (Y/N): ")
    newCreds(name, username, password, token, isProd)

def newConfig(name, rootPath):
    """Creates a new solenopsis config file

    name - The name of the base environment
    rootPath - The root path for where the SFDC data lives"""
    solenopsisPath = getConfigFile()
    homePath = os.path.expanduser(getHome())

    if os.path.exists(solenopsisPath) and not isForced():
        logger.critical('Solenopsis config file already exists "%s"' % (solenopsisPath,))
        sys.exit(-1)

    try:
        with open(solenopsisPath, 'w', encoding="utf-8") as file:
            file.write("%s=%s %s\n" % (CONFIG_NAMES["env"], name, 'local',))
            file.write("%s=%s\n" % (CONFIG_NAMES["home"], homePath,))
            file.write("%s=%s\n" % (CONFIG_NAMES["master"], 'local',))
            file.write("%s=%s\n" % (CONFIG_NAMES["dependent"], name,))
            file.write("%s=%s\n" % (CONFIG_NAMES["local-home"], rootPath,))

        credPath = os.path.expanduser(os.path.join(getHome(), 'credentials'))
        envPath = os.path.expanduser(os.path.join(getHome(), 'env'))

        if not os.path.exists(homePath):
            os.mkdir(homePath)

        if not os.path.exists(credPath):
            os.mkdir(credPath)

        if not os.path.exists(envPath):
            os.mkdir(envPath)
    except: # pylint: disable=bare-except
        logger.critical('An error occurred trying to write to "%s"' % (solenopsisPath,))
        sys.exit(-1)

def setup(name, username, password, token, isProd, rootPath): # pylint: disable=too-many-arguments
    """Sets up a new solenopsis environment

    name - The name of the dependent environment
    username - The usename
    password - The password
    token - The token
    isProd - Is the environment a production environment
    rootPath - The root path"""
    newConfig(name, rootPath)
    newCreds(name, username, password, token, isProd)

def setupInteractive():
    """An interactive setup for the initial solenopsis config file"""
    if hasConfigFile() and not isForced():
        logger.critical('config file already exists')
        sys.exit(-1)

    name = input("Please enter your environment name: ")
    username = input("Please enter your salesforce username: ")
    password = getpass.getpass(prompt="Please enter your salesforce password: ")
    token = input("Please enter your salesforce token: ")
    isProd = input("Is this a production instance? (Y/N): ")
    rootPath = input("Please enter that path to your src directory: ")

    setup(name, username, password, token, isProd, rootPath)

def hasConfigFile():
    """Checks to see if the config file exists"""
    return os.path.exists(getConfigFile())

def parseSolConfig():
    """Parses the solenopsis config file"""
    if not hasConfigFile():
        logger.critical('Unable to open solenopsis config file "%s"' % (getConfigFile(),))
        sys.exit(-1)

    try:
        configParser = parser.getParser()
        with open(os.path.expanduser(getDefaultConfig()), encoding="utf-8") as configFile:
            if osutils.isPython2():
                configParser.readfp(FakeSecHead(configFile)) # pylint: disable=deprecated-method
            else:
                configParser.read_file(FakeSecHead(configFile))

            setHome(configParser.get('section', 'solenopsis.env.HOME'))
            setMaster(configParser.get('section', 'solenopsis.env.MASTER'))

            if getDependent() is None:
                setDependent(configParser.get('section', 'solenopsis.env.DEPENDENT'))

            rawConfig = {}

            for (name, value) in configParser.items('section'):
                rawConfig[name.lower()] = value

            fname = getDependentFile()
            if os.path.isfile(fname):
                with open(fname, encoding="utf-8") as envFile:
                    if osutils.isPython2():
                        configParser.readfp(FakeSecHead(envFile)) # pylint: disable=deprecated-method
                    else:
                        configParser.read_file(FakeSecHead(envFile))
                    for (name, value) in configParser.items('section'):
                        rawConfig[name.lower()] = value

            setRawConfig(rawConfig)
    except: # pylint: disable=bare-except
        logger.critical('Unable to parse config file')
        sys.exit(-1)

def config(secondary):
    """The main function for running config commands

    secondary - The rest of the command after 'config'"""
    if len(secondary) == 0:
        logger.critical('No secondary command passed to config')
        sys.exit(-1)

    if secondary[0] == 'new':
        if len(secondary) < 2:
            logger.critical('No type of new config')
            sys.exit(-1)

        if secondary[1] == 'credential':
            newCredsInteractive()
    elif secondary[0] == 'setup':
        setupInteractive()
    else:
        logger.critical('Command "config %s" not found' % (secondary[0],))
