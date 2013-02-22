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


import sys
import urlparse

import logger
import environment

try:
    import beatbox
except:
    logger.warning('Could not import "beatbox" library')

def get_max_width(table, index):
    """Get the maximum width of the given column index"""
    lengths = [len(row[index]) for row in table]
    lengths.append(len(index))
    return max(lengths)

def query(q, dependent = None):
    result = None

    environment.parseSolConfig()
    creds = environment.getDependentCreds()

    if not dependent is None:
        creds = environment.getCreds(dependent)
        if creds is None:
            logger.critical("Could not read configuration for '%s'" % (dependent,))
            sys.exit(-1)

    if not creds["url"]:
        logger.critical("Url not specified in solenopsis configuration")
        sys.exit(-1)

    server_url = urlparse.urljoin(creds["url"], 'services/Soap/u/20.0')
    connection = beatbox.PythonClient(serverUrl=server_url)
    connection.login(creds["username"], '%s%s' % (creds["password"], creds["token"],))
    try:
        result = connection.query(q)
    except beatbox.SoapFaultError:
        logger.critical("Error with query\n %s" % sys.exc_info()[1])
        sys.exit(-1)
    return result

def prettyQuery(q, dependent = None):
    result = query(q, dependent)

    if len(result) == 0:
        logger.critical('No rows returned')
        sys.exit(-1)

    col_width = {}
    keys = result[0].keys()
    keys.remove('type')
    for key in keys:
        col_width[key] = get_max_width(result, key)

    for key in keys:
        print key.ljust(col_width[key] + 1),
    print ""

    for row in result:
        for key in keys:
            print row[key].ljust(col_width[key] + 1),
        print ""