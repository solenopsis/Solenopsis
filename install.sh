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

#
# Make sure any params processed were consumed.  We know consumption is goo
# if the params handed to this function ($1) is empty.
#
assertGoodParams() {
    if [ "$1" != "" ]
    then
        echo
        echo "Allowable command line options:"
        echo "    --local       When installing locally, will clone from the Solenopsis git repo."
        echo "    --use-git     If cloning locally, will use a git URL:   git clone git://github.com/solenopsis/Solenopsis.git"
        echo "    --use-ssh     If cloning locally, will use an ssh URL:  git clone ssh://github.com/solenopsis/Solenopsis.git"
        echo
        echo "    By default when assuming no clone option presented, will use:"
        echo "        git clone http://github.com/solenopsis/Solenopsis.git"
        echo
        exit 1
    fi
}

RUN_DIR=`dirname $0`

RUN_LOCAL=""
GIT_PROTOCOL="https"
BAD_PARAM=""

echo

#
# Process params handed in...
#
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
    else
        #
        # Unknown param...
        #
        echo "*** WARNING:  Unknown param [${PARAM}]"
        BAD_PARAM="${BAD_PARAM} ${PARAM}"
	fi
done

#
# Should we have concatenated all bad params, we will
# fail here with a useful command line options message
# and exit...
#
assertGoodParams "${BAD_PARAM}"

${RUN_DIR}/uninstall.sh

echo

if [ "${RUN_LOCAL}" = "" ]
then
	rm -rf Solenopsis

	CLONE_CMD="git clone ${GIT_PROTOCOL}://github.com/solenopsis/Solenopsis.git"
	echo "Cloning the solenopsis git repo via:  [${CLONE_CMD}]"
    echo
	${CLONE_CMD}
    echo

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

echo "Installing solenopsis for [${RUNNING_OS}] to:  [${SOLENOPSIS_INSTALL_HOME}]..."

echo "    Creating appropriate directory structure..."

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

echo "    Copying configuration files..."

cp config/defaults.cfg $SOLENOPSIS_INSTALL_HOME/solenopsis/config/
cp docs/* $SOLENOPSIS_INSTALL_HOME/solenopsis/docs/
cp -rf ant $SOLENOPSIS_INSTALL_HOME/solenopsis
cp scripts/solenopsis $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/
cp scripts/bsolenopsis $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/
cp scripts/lib/* $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/lib/
cp scripts/templates/* $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/templates/
cp scripts/solenopsis-profile.sh $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/

echo "    Cleaning up old Python files..."

rm -rf $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/*.pyc
rm -rf $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/*.pyo
rm -rf $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/lib/*.pyc
rm -rf $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/lib/*.pyo

echo "    Setting script permisions to execute and creating sym links..."

chmod 755 $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/*

ln -sf $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/solenopsis $SOLENOPSIS_BINARIES/solenopsis
ln -sf $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/bsolenopsis $SOLENOPSIS_BINARIES/bsolenopsis

case $RUNNING_OS in
    Linux)
	    SOLENOPSIS_BASH_COMPLETION_HOME=/etc/bash_completion.d
	    ln -sf $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/solenopsis-profile.sh $SOLENOPSIS_PROFILE_PATH/solenopsis-profile.sh
        ;;
esac

echo "    Defining Solenopsis bash completion..."

if [ -d "${SOLENOPSIS_BASH_COMPLETION_HOME}" ]
then
    ln -sf $SOLENOPSIS_INSTALL_HOME/solenopsis/scripts/solenopsis-completion.bash ${SOLENOPSIS_BASH_COMPLETION_HOME}/solenopsis-completion.bash
else
    echo "WARNING:  Bash completion dir [${SOLENOPSIS_BASH_COMPLETION_HOME}] for [${RUNNING_OS}] does not exist.  Ignoring solenopsis completion script."
fi

echo