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

"""Handles the parser differences between python2 and python3"""

from . import osutils

def getParser():
    """Gets the parser based on the python version"""
    parser = None

    if osutils.isPython2():
        import ConfigParser # pylint: disable=import-outside-toplevel,import-error

        parser = ConfigParser.ConfigParser()
    else:
        import configparser # pylint: disable=import-outside-toplevel

        parser = configparser.ConfigParser()

    return parser
