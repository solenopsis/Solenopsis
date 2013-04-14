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


NAME = solenopsis
SPECFILE = $(NAME).spec
VERSION = 1.2
# this is ugly, any suggestions?
GIT = $(shell echo `git log | head -1 | awk '{print $$2}' | head -c 8`)
PWDNOW=$(PWD)

all:

.PHONY: install tarball clean

clean:
	rm -rf ./dist

tarball: clean
	sed -i 's/Version: .*/Version:  $(VERSION)/g' $(SPECFILE)
	mkdir -p /tmp/$(NAME)-$(VERSION)
	cp -a * /tmp/$(NAME)-$(VERSION)/
	tar czf $(NAME)-$(VERSION).tar.gz -C /tmp $(NAME)-$(VERSION)
	mkdir -p dist
	mv $(NAME)-$(VERSION).tar.gz dist
	rm -rf /tmp/$(NAME)-$(VERSION)

rpm: tarball
# fix release in the .spec?
	cp dist/$(NAME)-$(VERSION).tar.gz $(RPM_SOURCE_DIR)
	rpmbuild --define "_topdir $(RPM_BUILD_ROOT)" -ba $(NAME).spec