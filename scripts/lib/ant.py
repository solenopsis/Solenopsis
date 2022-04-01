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


"""This class is designed as a wrapper for the ant methods.  Abstracts around the
various ant portions."""

__author__ = "Patrick Connelly (patrick@deadlypenguin.com)"
__version__ = "1.1"

import os
import sys
import glob

from . import logger

ANT_FLAGS = ""
BUILD_XML = "/usr/share/solenopsis/ant/solenopsis.xml"
JAVA_PREFIX = (
    "java -Dhttps.protocols='TLSv1.1,TLSv1.2' "
    "-classpath __CLASSPATH__ "
    "-Dant.home=/usr/share/solenopsis/ant org.apache.tools.ant.Main"
)
ANT_LIB_DIR = "/usr/share/solenopsis/ant/lib/1.9.6/"

ROOT_DIR = None

def setRootDir(path):
    """Sets the root dir for SFDC data

    path - The path
    """
    global ROOT_DIR # pylint: disable=global-statement
    ROOT_DIR = path

def getRootDir():
    """Gets the root dir for SFDC data"""
    return ROOT_DIR

def setBuildXML(path):
    """Sets the Build XML path

    path - The new build path
    """
    global BUILD_XML # pylint: disable=global-statement
    BUILD_XML=path

def getBuildXML():
    """Gets the build XML path"""
    return BUILD_XML

def addFlag(flag):
    """Adds an ant flag to the current flag list

    flag - The flag to add
    """
    global ANT_FLAGS # pylint: disable=global-statement
    ANT_FLAGS += ' -D%s' % (flag,)

def getFlags():
    """Gets the ant flags"""
    return ANT_FLAGS

def getJavaPrefix():
    """Gets the java classpath and adds it to the prefix"""
    classpath = ':'.join(glob.glob(ANT_LIB_DIR+'*'))
    return JAVA_PREFIX.replace('__CLASSPATH__', classpath)

def runAnt(action):
    """Runs the ant action given

    action - The action to run
    """
    try:
        runString = '%s %s -f %s %s' % (getJavaPrefix(), getFlags(), getBuildXML(), action,)
        logger.debug('Running ant command "%s"' % (runString,))
        retcode = os.system(runString)
        sys.exit(retcode >> 8)
    except OSError as error:
        print("Error running ant action '%s'" % (error.strerror,))

def push():
    """Does a push to SFDC"""
    runAnt('push')

def gitPush():
    """Does a push of things that have changed in git"""
    runAnt('git-push')

def filePush(fileList):
    """Pushes individual files to SFDC

    fileList - An array of file names to push
    """
    if len(fileList) == 0:
        logger.critical('No files listed to push')
        sys.exit(-1)

    fileList = ''

    for fname in fileList:
        filePath = os.path.join(os.path.expanduser(getRootDir()), fname)
        if os.path.exists(filePath):
            fileList = "%s%s%s" %(fileList, fname, os.pathsep,)
        else:
            logger.warning('Unable to find file "%s".  Skipping.' % (filePath,))

    if fileList != '':
        # fileList[:-2]
        addFlag('%s=\'%s\'' % ('sf.files2push', fileList,))
        runAnt('file-push')
    else:
        logger.critical('Unable to find any files to push.')
        sys.exit(-1)

def fileDestructivePush(fileList):
    """Pushes individual files to SFDC

    fileList - An array of file names to push
    """
    if len(fileList) == 0:
        logger.critical('No files listed to push')
        sys.exit(-1)

    fileList = ''

    for fname in fileList:
        filePath = os.path.join(os.path.expanduser(getRootDir()), fname)
        if os.path.exists(filePath):
            fileList = "%s%s%s" %(fileList, fname, os.pathsep,)
        else:
            logger.warning('Unable to find file "%s".  Skipping.' % (filePath,))

    if fileList != '':
        # fileList[:-2]
        addFlag('%s=\'%s\'' % ('sf.files2remove', fileList,))
        runAnt('file-destructive-push')
    else:
        logger.critical('Unable to find any files to push.')
        sys.exit(-1)

def destructivePush():
    """Does a destructive push to SFDC"""
    runAnt('destructive-push')

def gitDestructivePush():
    """Does a destructive push to SFDC of what has changed in git"""
    runAnt('git-destructive-push')

def cachedDestructivePush():
    """Does a destructive push to SFDC without re-pulling from SFDC"""
    runAnt('cached-destructive-push')

def pullFull():
    """Does a pull from SFDC to a temp dir"""
    runAnt('pull-full')

def pullFullToMaster():
    """Does a pull from SFDC to the root directory for that environment"""
    runAnt('pull-full-to-master')

def pull():
    """Does a pull from SFDC to a temp dir"""
    runAnt('pull')

def pullToMaster():
    """Does a pull from SFDC to the root directory for that environment"""
    runAnt('pull-to-master')

def reportDiff():
    """Reports the difference between two SFDC instances"""
    runAnt('report-diff')

def generatePackage():
    """Generates a package from SFDC"""
    runAnt('generate-package')

def generateFullPackage():
    """Generates a package from SFDC"""
    runAnt('generate-full-package')

def selectivePull():
    """Does a selective pull"""
    runAnt('selective-pull')

def selectivePullToMaster():
    """Does a selective pull to master"""
    runAnt('selective-pull-to-master')

def describeMetadata():
    """Does a metadata describe from SFDC"""
    runAnt('describe-metadata')

def listMetadata(metadataList):
    """Does a metadata list from SFDC"""
    addFlag('%s="%s"' % ('sf.metadataTypes', metadataList,))
    runAnt('list-metadata')

def runTests():
    """Runs all the tests in SFDC"""
    runAnt('run-tests')

def deltaPush():
    """Pushes only the files that have changed from the master"""
    runAnt('delta-push')

def cachedDeltaPush():
    """Runs a delta push with cached information"""
    runAnt('cached-delta-push')
