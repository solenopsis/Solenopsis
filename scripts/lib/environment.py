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
import ConfigParser
import getpass

import logger

HOME = '~/.solenopsis/'
FORCE = False
MASTER = None
DEPENDENT = None
DEFAULT_CONFIG = '~/solenopsis.properties'

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

class FakeSecHead(object):
    """ This is a horrible hack but since ConfigParser doesn't handle .properties
        files"""
    def __init__(self, fp):
        """When we init the class create a file pointer and a fack section header"""
        self.fp = fp
        self.sechead = '[section]\n'
    def readline(self):
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
    global DEFAULT_CONFIG
    DEFAULT_CONFIG = path

def getDefaultConfig():
    """Get the default config path"""
    return DEFAULT_CONFIG

def setRawConfig(cfg):
    """Set the raw config information.  This is all sections from the file

    cfg - A dict of the key/value pars in the solenopsis config file"""
    global RAW_CONFIG
    RAW_CONFIG = cfg

def getRawConfig():
    """Get the raw config"""
    return RAW_CONFIG

def setHome(path):
    """Set the home for solenopsis credentials and environments

    path - The name """
    global HOME
    HOME = path

def getHome():
    """Gets the home"""
    return HOME

def setMaster(name):
    """Sets the name of the master environment

    name - The name of the environment"""
    global MASTER
    MASTER = name

def getMaster():
    """Gets the name of the master environment"""
    return MASTER

def setDependent(name):
    """Sets the name of the dependent environment

    name - The name of the environment"""
    global DEPENDENT
    DEPENDENT = name

def getDependent():
    """Gets the name of the dependent environment"""
    return DEPENDENT

def setForce(status):
    """Sets if we should force overwrite config files

    status - True/False"""
    global FORCE
    FORCE = status

def isForced():
    """Returns if we should force overwrite config files"""
    return FORCE

def setUsername(name):
    """Sets the username for the current credential file

    name - The username"""
    global USERNAME
    USERNAME = name

def getUsername():
    """Gets the username for the current credential file"""
    return USERNAME

def setPassword(pw):
    """Sets the password for the current credential file

    pw - The password"""
    global PASSWORD
    PASSWORD = pw

def getPassword():
    """Gets the password for the current credential file"""
    return PASSWORD

def setToken(tok):
    """Sets the token for the current credential file

    tok - The token"""
    global TOKEN
    TOKEN = tok

def getToken():
    """Gets the token for the current credential file"""
    return TOKEN

def setUrl(u):
    """Sets the url for the current credential file

    u - The url"""
    global URL
    URL = u

def getUrl():
    """Gets the url for the current credential file"""
    return URL

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

    new_filename = '%s.properties' % (name,)
    cred_path = os.path.expanduser(os.path.join(getHome(), 'credentials', new_filename))

    is_production = False

    if isProd.upper() == 'Y' or isProd.upper() == 'YES':
        is_production = True

    if os.path.exists(cred_path) and not isForced():
        logger.critical('Credential file already exists "%s"' % (cred_path,))
        sys.exit(-1)

    url = URL_MAP["dev"]
    if is_production:
        url = URL_MAP["prod"]

    try:
        f = open(cred_path, 'w')
        f.write("%s=%s\n" % (CREDENTIAL_NAMES["username"], username,))
        f.write("%s=%s\n" % (CREDENTIAL_NAMES["password"], password,))
        f.write("%s=%s\n" % (CREDENTIAL_NAMES["token"], token,))
        f.write("%s=%s\n" % (CREDENTIAL_NAMES["url"], url,))

        f.close()
    except Exception as e:
        logger.critical('An error occurred trying to write to "%s": %s' % (cred_path, e.strerror))
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

def parseCreds(name):
    """Reads the credentials file

    name - The environment name"""
    if not getHome():
        logger.critical('Home not set')
        sys.exit(-1)

    cred_filename = '%s.properties' % (name,)
    cred_path = os.path.expanduser(os.path.join(getHome(), 'credentials', cred_filename))

    try:
        config = ConfigParser.ConfigParser()
        config.readfp(FakeSecHead(open(cred_path)))
        setUsername(config.get('section', 'username'))
        setPassword(config.get('section', 'password'))
        setToken(config.get('section', 'token'))
        if config.has_option('section', 'url'):
            setUrl(config.get('section', 'url'))
    except:
        logger.critical('An error occurred trying to read "%s"' % (cred_path))
        sys.exit(-1)

def newCredsInteractive():
    """Prompts interactively for new credentials"""
    name = raw_input("Please enter your environment name: ")
    username = raw_input("Please enter your salesforce username: ")
    password = getpass.getpass(prompt="Please enter your salesforce password: ")
    token = raw_input("Please enter your salesforce token: ")
    isProd = raw_input("Is this a production instance? (Y/N): ")
    newCreds(name, username, password, token, isProd)

def newConfig(name, root_path):
    """Creates a new solenopsis config file

    name - The name of the base environment
    root_path - The root path for where the SFDC data lives"""
    solenopsis_path = os.path.expanduser(getDefaultConfig())
    home_path = os.path.expanduser(getHome())
    if os.path.exists(solenopsis_path) and not isForced():
        logger.critical('Solenopsis config file already exists "%s"' % (solenopsis_path,))
        sys.exit(-1)

    try:
        f = open(solenopsis_path, 'w')
        f.write("%s=%s %s\n" % (CONFIG_NAMES["env"], name, 'local',))
        f.write("%s=%s\n" % (CONFIG_NAMES["home"], home_path,))
        f.write("%s=%s\n" % (CONFIG_NAMES["master"], 'local',))
        f.write("%s=%s\n" % (CONFIG_NAMES["dependent"], name,))
        f.write("%s=%s\n" % (CONFIG_NAMES["local-home"], root_path,))
        f.close()

        cred_path = os.path.expanduser(os.path.join(getHome(), 'credentials'))
        env_path = os.path.expanduser(os.path.join(getHome(), 'env'))

        if not os.path.exists(home_path):
            os.mkdir(home_path)

        if not os.path.exists(cred_path):
            os.mkdir(cred_path)

        if not os.path.exists(env_path):
            os.mkdir(env_path)
    except:
        logger.critical('An error occured trying to write to "%s"' % (solenopsis_path,))
        sys.exit(-1)

def setup(name, username, password, token, isProd, root_path):
    """Sets up a new solenopsis environment

    name - The name of the dependent environment
    username - The usename
    password - The password
    token - The token
    isProd - Is the environment a produciton environment
    root_path - The root path"""
    newConfig(name, root_path)
    newCreds(name, username, password, token, isProd)

def setupInteractive():
    """An interactive setup for the initial solenopsis config file"""
    if hasConfigFile() and not isForced():
        logger.critical('config file already exists')
        sys.exit(-1)

    name = raw_input("Please enter your environment name: ")
    username = raw_input("Please enter your salesforce username: ")
    password = getpass.getpass(prompt="Please enter your salesforce password: ")
    token = raw_input("Please enter your salesforce token: ")
    isProd = raw_input("Is this a production instance? (Y/N): ")
    root_path = raw_input("Please enter that path to your src directory: ")

    setup(name, username, password, token, isProd, root_path)

def hasConfigFile():
    """Checks to see if the config file exists"""
    solenopsis_path = os.path.expanduser(getDefaultConfig())
    return os.path.exists(solenopsis_path)

def parseSolConfig():
    """Parses the solenopsis config file"""
    if not hasConfigFile():
        logger.critical('Unable to open solenopsis config file "%s"' % (solenopsis_path,))
        sys.exit(-1)

    try:
        config = ConfigParser.ConfigParser()
        config.readfp(FakeSecHead(open(os.path.expanduser(getDefaultConfig()))))

        setHome(config.get('section', 'solenopsis.env.HOME'))
        setMaster(config.get('section', 'solenopsis.env.MASTER'))

        if getDependent() == None:
            setDependent(config.get('section', 'solenopsis.env.DEPENDENT'))

        raw_config = {}

        for (name, value) in config.items('section'):
            raw_config[name.lower()] = value

        fname = os.path.expanduser('~/.solenopsis/environments/' + getDependent() + '.properties');
        if os.path.isfile(fname):
            config.readfp(FakeSecHead(open(fname)))
            for (name, value) in config.items('section'):
                raw_config[name.lower()] = value

        setRawConfig(raw_config)
    except:
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