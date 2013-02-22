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


"""This class to handle logging between all of the different libraries
"""

import logging
import sys

# Init "logging".  Used to print different levels of debug info
logger = logging.getLogger('stdout')
stream = logging.StreamHandler(sys.stdout)
logger.addHandler(stream)

def debug(msg):
    """Log a debug level message

    msg - The message
    """
    global logger
    logger.debug(msg)

def info(msg):
    """Log an info level message

    msg - The message
    """
    global logger
    logger.info(msg)

def warning(msg):
    """Log a warning level message

    msg - The message
    """
    global logger
    logger.warning(msg)

def error(msg):
    """Log a error level message

    msg - The message
    """
    global logger
    logger.error(msg)

def critical(msg):
    """Log a critical level message

    msg - The message
    """
    global logger
    logger.critical(msg)

def setLevel(level):
    """Set the debug level

    leve - The level
    """
    global logger
    logger.setLevel(level)