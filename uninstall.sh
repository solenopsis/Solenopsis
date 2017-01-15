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

echo "Cleaning up existing Solenopsis..."

#
# Take OSX, Linux, etc into account...
#
RUNNING_OS=`uname -s`

case $RUNNING_OS in
    Linux)
        SOLENOPSIS_BASH_COMPLETION_HOME=/etc/bash_completion.d
        SOLENOPSIS_INSTALL_HOME=/usr/share
        SOLENOPSIS_BINARIES=/usr/bin
        rm -f /etc/bash_completion.d/solenopsis-completion.bash
        rm -f /etc/profile.d/solenopsis-profile.sh
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

rm -f $SOLENOPSIS_BINARIES/solenopsis
rm -f $SOLENOPSIS_BINARIES/bsolenopsis

rm -rf $SOLENOPSIS_INSTALL_HOME/solenopsis