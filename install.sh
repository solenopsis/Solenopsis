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
GIT_PROTOCOL="https"

for PARAM in $*
do
	if [ "${PARAM}" = "--local" ]
	then
		RUN_LOCAL=${PARAM}
	elif [ "${PARAM}" = "--use-git" ]
	then
		GIT_PROTOCOL="git"
	elif [ "${PARAM}" = "--use-ssh" ]
	then
		GIT_PROTOCOL="ssh"
	fi
done

if [ "${RUN_LOCAL}" = "" ]
then
	echo "Cloning the solenopsis git repo"

	rm -rf Solenopsis

	CLONE_CMD="git clone ${GIT_PROTOCOL}://github.com/solenopsis/Solenopsis.git"
	echo "${CLONE_CMD}"
	`${CLONE_CMD}`

	cd Solenopsis
else
	cd ${RUN_DIR}
fi


RUNNING_OS=`uname -s`

case $RUNNING_OS in
    Linux)
	SOLENOPSIS_BASH_COMPLETION_HOME=/etc/bash_completion.d
	SOLENOPSIS_INSTALL_HOME=/usr/share
	SOLENOPSIS_BINARIES=/usr/bin
	SOLENOPSIS_PROFILE_PATH=/etc/profile.d
        ;;
    Darwin)
	SOLENOPSIS_BASH_COMPLETION_HOME=/usr/local/etc/bash_completion.d
	SOLENOPSIS_BINARIES=/usr/local/bin
	SOLENOPSIS_PROFILE_PATH=
	if type brew >/dev/null; then
		SOLENOPSIS_INSTALL_HOME=/usr/local/Cellar
	else
		SOLENOPSIS_INSTALL_HOME=/usr/share
	fi
        ;;
esac

echo "Installing solenopsis"

mkdir -p $SOLENOPSIS_INSTALL_HOME/solenopsis/config
mkdir -p $SOLENOPSIS_INSTALL_HOME/solenopsis/docs
mkdir -p $SOLENOPSIS_INSTALL_HOME/solenopsis/ant
mkdir -p $SOLENOPSIS_INSTALL_HOME/solenopsis/ant/lib
mkdir -p $SOLENOPSIS_INSTALL_HOME/solenopsis/ant/lib/1.8.4
mkdir -p $SOLENOPSIS_INSTALL_HOME/solenopsis/ant/lib/1.9.1
mkdir -p $SOLENOPSIS_INSTALL_HOME/solenopsis/ant/1.1/lib
mkdir -p $SOLENOPSIS_INSTALL_HOME/solenopsis/ant/1.1/properties
mkdir -p $SOLENOPSIS_INSTALL_HOME/solenopsis/ant/1.1/templates
mkdir -p $SOLENOPSIS_INSTALL_HOME/solenopsis/ant/1.1/util
mkdir -p $SOLENOPSIS_INSTALL_HOME/solenopsis/ant/1.2/bsh
mkdir -p $SOLENOPSIS_INSTALL_HOME/solenopsis/ant/1.2/lib
mkdir -p $SOLENOPSIS_INSTALL_HOME/solenopsis/ant/1.2/properties
mkdir -p $SOLENOPSIS_INSTALL_HOME/solenopsis/ant/1.2/xslt
mkdir -p $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts
mkdir -p $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/lib
mkdir -p $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/templates

cp config/defaults.cfg $SOLENOPSIS_INSTALL_HOME/solenopsis/config/
cp docs/* $SOLENOPSIS_INSTALL_HOME/solenopsis/docs/
cp -rf ant $SOLENOPSIS_INSTALL_HOME/solenopsis
cp scripts/solenopsis $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/
cp scripts/bsolenopsis $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/
cp scripts/lib/* $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/lib/
cp scripts/templates/* $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/templates/
cp scripts/solenopsis-profile.sh $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/


rm -rf $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/*.pyc
rm -rf $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/*.pyo
rm -rf $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/lib/*.pyc
rm -rf $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/lib/*.pyo

chmod 755 $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/*

ln -sf $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/solenopsis $SOLENOPSIS_BINARIES/solenopsis
ln -sf $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/bsolenopsis $SOLENOPSIS_BINARIES/bsolenopsis

case $RUNNING_OS in
    Linux)
	SOLENOPSIS_BASH_COMPLETION_HOME=/etc/bash_completion.d
	ln -sf $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/solenopsis-profile.sh $SOLENOPSIS_PROFILE_PATH/solenopsis-profile.sh
        ;;
esac

if [ -d "${SOLENOPSIS_BASH_COMPLETION_HOME}" ]
then
    ln -sf $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/solenopsis-completion.bash ${SOLENOPSIS_BASH_COMPLETION_HOME}/solenopsis-completion.bash
else
    echo "WARNING:  Bash completion dir [${SOLENOPSIS_BASH_COMPLETION_HOME}] for [${RUNNING_OS}] does not exist.  Ignoring solenopsis completion script."
fi
