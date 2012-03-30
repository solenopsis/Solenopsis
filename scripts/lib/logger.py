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
