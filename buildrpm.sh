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

if [ $# = "0" ]
then
	RPM_HOME=~/.solenopsis
else
	RPM_HOME=$1
fi

echo "Building RPM in ${RPM_HOME}/rpm..."

export RPM_BUILD_ROOT=${RPM_HOME}/rpm
export RPM_BUILD_DIR=${RPM_HOME}/rpm/BUILD
export RPM_ROOT_DIR=${RPM_HOME}/rpm
export RPM_SOURCE_DIR=${RPM_HOME}/rpm/SOURCES

mkdir -p ${RPM_BUILD_ROOT}/BUILD
mkdir -p ${RPM_BUILD_ROOT}/RPMS
mkdir -p ${RPM_BUILD_ROOT}/SOURCES
mkdir -p ${RPM_BUILD_ROOT}/SPECS
mkdir -p ${RPM_BUILD_ROOT}/SRPMS

OLD_DIR=`pwd`

cd `dirname $0`

make rpm

cd ${OLD_DIR}