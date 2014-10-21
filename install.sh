#!/bin/bash

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

# Install script for *nix based operating systems that do not support RPMs

RUN_DIR=`dirname $0`

${RUN_DIR}/uninstall.sh

RUN_LOCAL=""

for PARAM in $*
do
	if [ "${PARAM}" = "--local" ]
	then
		RUN_LOCAL=${PARAM}
	fi
done

if [ "${RUN_LOCAL}" = "" ]
then
	echo "Cloning the solenopsis git repo"

	rm -rf Solenopsis

	git clone git://github.com/solenopsis/Solenopsis.git

	cd Solenopsis
else
	cd ${RUN_DIR}
fi

echo "Installing solenopsis"

mkdir -p /usr/share/solenopsis/config
mkdir -p /usr/share/solenopsis/docs
mkdir -p /usr/share/solenopsis/ant
mkdir -p /usr/share/solenopsis/ant/lib
mkdir -p /usr/share/solenopsis/ant/lib/1.8.4
mkdir -p /usr/share/solenopsis/ant/lib/1.9.1
mkdir -p /usr/share/solenopsis/ant/1.1/lib
mkdir -p /usr/share/solenopsis/ant/1.1/properties
mkdir -p /usr/share/solenopsis/ant/1.1/templates
mkdir -p /usr/share/solenopsis/ant/1.1/util
mkdir -p /usr/share/solenopsis/ant/1.2/bsh
mkdir -p /usr/share/solenopsis/ant/1.2/lib
mkdir -p /usr/share/solenopsis/ant/1.2/properties
mkdir -p /usr/share/solenopsis/ant/1.2/xslt
mkdir -p /usr/share/solenopsis/scripts
mkdir -p /usr/share/solenopsis/scripts/lib
mkdir -p /usr/share/solenopsis/scripts/templates

cp config/defaults.cfg /usr/share/solenopsis/config/
cp docs/* /usr/share/solenopsis/docs/
cp -rf ant /usr/share/solenopsis
cp scripts/solenopsis /usr/share/solenopsis/scripts/
cp scripts/bsolenopsis /usr/share/solenopsis/scripts/
cp scripts/lib/* /usr/share/solenopsis/scripts/lib/
cp scripts/templates/* /usr/share/solenopsis/scripts/templates/
cp scripts/solenopsis-profile.sh /usr/share/solenopsis/scripts/


rm -rf /usr/share/solenopsis/scripts/*.pyc
rm -rf /usr/share/solenopsis/scripts/*.pyo
rm -rf /usr/share/solenopsis/scripts/lib/*.pyc
rm -rf /usr/share/solenopsis/scripts/lib/*.pyo

chmod 755 /usr/share/solenopsis/scripts/*

ln -sf /usr/share/solenopsis/scripts/solenopsis /usr/bin/solenopsis
ln -sf /usr/share/solenopsis/scripts/bsolenopsis /usr/bin/bsolenopsis
ln -sf /usr/share/solenopsis/scripts/solenopsis-profile.sh /etc/profile.d/solenopsis-profile.sh

#
# Take OSX, Linux, etc into account...
#
RUNNING_OS=`uname -a | cut -f 1 -d ' '`

case $RUNNING_OS in
    Linux) SOLENOPSIS_BASH_COMPLETION_HOME=/etc/bash_completion.d
        ;;
    Darwin) SOLENOPSIS_BASH_COMPLETION_HOME=/usr/local/etc/bash_completion.d
        ;;
esac

if [ -d "${SOLENOPSIS_BASH_COMPLETION_HOME}" ]
then
    ln -sf /usr/share/solenopsis/scripts/solenopsis-completion.bash ${SOLENOPSIS_BASH_COMPLETION_HOME}/solenopsis-completion.bash
else
    echo "WARNING:  Bash completion dir [${SOLENOPSIS_BASH_COMPLETION_HOME}] for [${RUNNING_OS}] does not exist.  Ignoring solenopsis completion script."
fi