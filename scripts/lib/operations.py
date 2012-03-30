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