NAME = solenopsis
SPECFILE = $(NAME).spec
VERSION = 1.1
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
