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

echo "Cloning the solenopsis git repo"

rm -rf Solenopsis

git clone git://github.com/solenopsis/Solenopsis.git

cd Solenopsis

echo "Installing solenopsis"

cp config/defaults.cfg /usr/share/solenopsis/config/
cp docs/* /usr/share/solenopsis/docs/
cp ant/solenopsis-setup.xml /usr/share/solenopsis/ant/
cp ant/solenopsis.xml /usr/share/solenopsis/ant/
cp ant/lib/ant/* /usr/share/solenopsis/ant/lib/ant/
cp ant/lib/*.jar /usr/share/solenopsis/ant/lib/
cp ant/properties/* /usr/share/solenopsis/ant/properties/
cp ant/templates/* /usr/share/solenopsis/ant/templates/
cp ant/util/* /usr/share/solenopsis/ant/util/
cp scripts/solenopsis /usr/share/solenopsis/scripts/
cp scripts/bsolenopsis /usr/share/solenopsis/scripts/
cp scripts/bsolenopsisant /usr/share/solenopsis/scripts/
cp scripts/lib/* /usr/share/solenopsis/scripts/lib/
cp scripts/templates/* /usr/share/solenopsis/scripts/templates/
cp scripts/solenopsis-completion.bash /usr/share/solenopsis/scripts/
cp scripts/solenopsis-profile.sh /usr/share/solenopsis/scripts/

chmod 755 /usr/share/solenopsis/scripts/*

ln -sf /usr/share/solenopsis/scripts/solenopsis /usr/bin/solenopsis
ln -sf /usr/share/solenopsis/scripts/bsolenopsis /usr/bin/bsolenopsis
ln -sf /usr/share/solenopsis/scripts/bsolenopsisant /usr/bin/bsolenopsisant
ln -sf /usr/share/solenopsis/scripts/solenopsis-completion.bash /etc/bash_completion.d/solenopsis-completion.bash
ln -sf /usr/share/solenopsis/scripts/solenopsis-profile.sh /etc/profile.d/solenopsis-profile.sh