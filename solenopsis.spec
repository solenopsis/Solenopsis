Summary: A set of scripts to help aid in Salesforce.com development and deployment
Name: solenopsis
Version:  1.1
Release: 6
URL: http://apps.gss.redhat.com/
License: GPL
Group: Applications/Internet
Source0: %{name}-%{version}.tar.gz
BuildRoot: %{_tmppath}/%{name}-%{version}-%{release}-root
BuildArch: noarch
Requires: python
Requires: python-argparse

%description
A set of scripts to help aid in Salesforce.com development and deployment.

Includes:
- Ant script to deploy to enviroments
- Wrapper script to aid in deployment
- Wrapper script to aid in trigger, class and page development

%prep
%setup -q

%build

%install
%{__rm} -rf %{buildroot}
%{__mkdir_p} %{buildroot}/usr/share/%{name}
%{__mkdir_p} %{buildroot}/usr/share/%{name}/config
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant/lib
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant/properties
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant/templates
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant/util
%{__mkdir_p} %{buildroot}/usr/share/%{name}/scripts
%{__mkdir_p} %{buildroot}/usr/share/%{name}/scripts/lib
%{__mkdir_p} %{buildroot}/usr/share/%{name}/scripts/templates

%{__install} -p -m 0755 config/defaults.cfg %{buildroot}/usr/share/%{name}/config/
%{__install} -p -m 0755 ant/solenopsis-setup.xml %{buildroot}/usr/share/%{name}/ant/
%{__install} -p -m 0755 ant/solenopsis.xml %{buildroot}/usr/share/%{name}/ant/
%{__install} -p -m 0755 ant/lib/* %{buildroot}/usr/share/%{name}/ant/lib/
%{__install} -p -m 0755 ant/properties/* %{buildroot}/usr/share/%{name}/ant/properties/
%{__install} -p -m 0755 ant/templates/* %{buildroot}/usr/share/%{name}/ant/templates/
%{__install} -p -m 0755 ant/util/* %{buildroot}/usr/share/%{name}/ant/util/
%{__install} -p -m 0755 scripts/solenopsis %{buildroot}/usr/share/%{name}/scripts/
%{__install} -p -m 0755 scripts/lib/* %{buildroot}/usr/share/%{name}/scripts/lib/
%{__install} -p -m 0755 scripts/templates/* %{buildroot}/usr/share/%{name}/scripts/templates/
%{__install} -p -m 0755 scripts/solenopsis-completion.bash %{buildroot}/usr/share/%{name}/scripts/
%{__install} -p -m 0755 scripts/solenopsis-profile.sh %{buildroot}/usr/share/%{name}/scripts/

%pre
rm -f /usr/bin/solenopsis
rm -f /etc/bash_completion.d/solenopsis-completion.bash
rm -f /etc/profile.d/solenopsis-profile.sh

%posttrans
ln -sf /usr/share/%{name}/scripts/solenopsis /usr/bin/solenopsis
ln -sf /usr/share/%{name}/scripts/solenopsis-completion.bash /etc/bash_completion.d/solenopsis-completion.bash
ln -sf /usr/share/%{name}/scripts/solenopsis-profile.sh /etc/profile.d/solenopsis-profile.sh

%preun
rm -f /usr/bin/solenopsis
rm -f /etc/bash_completion.d/solenopsis-completion.bash
rm -f /etc/profile.d/solenopsis-profile.sh

%clean
rm -rf %{buildroot}

%files
%attr(0755, root, root) /usr/share/%{name}/*

%changelog
* Fri Mar 30 2012 Patrick Connelly <patrick@deadlypenguin.com> 1.1-6
- Moving to github and removing ant-salesforce.jar
* Thu Feb 09 2012 Patrick Connelly <pconnell@redhat.com> 1.1-5
- Fixing dependent picking via -e flag
- Better error handling with query via beatbox
- Updating default api_version to 23 and fixing error with not having decimal place
* Tue Jan 24 2012 Patrick Connelly <pconnell@redhat.com> 1.1-4
- Updating scripts, templates and maxPoll
- Moving from na7 to login for prod url
- bumping up maxPoll
- fixing list-metadata
- Fixing describe-metadata
- Adding class-trigger template and new ant pass-thrus
- now defaulting to the path separator char for the delimiter on the metadata types
- added capability to describe metadata as well as list metadata
* Wed Nov 09 2011 Patrick Connelly <pconnell@redhat.com> 1.1-3
- Fixing problem where return code from ant was being devoured
* Fri Nov 04 2011 Patrick Connelly <pconnell@redhat.com> 1.1-2
- Fixing problem with formatting and python 2.4
* Fri Nov 04 2011 Scot Floess <sfloess@redhat.com> 1.1-1
- Revving salesforce jar to version 24
- Adding support for permissionsets
* Fri Oct 14 2011 Patrick Connelly <pconnell@redhat.com> 1.1-0
- Large rewrite of solenopsis wrapper script
- Fixing issue with git-push and multiple files
- Adding selective file push
- Adding destructive git-push
* Mon May 23 2011 Patrick Connelly <pconnell@redhat.com> 1.0-5
- Fixing symlink issue
* Mon May 23 2011 Patrick Connelly <pconnell@redhat.com> 1.0-4
- Adding new jar for Summer 11
- Changing config to use API version 22
* Fri May 13 2011 Patrick Connelly <pconnell@redhat.com> 1.0-3
- Adding destructive-push to solenopsis wrapper script
- Adding bash completion
* Wed Mar 16 2011 Patrick Connelly <pconnell@redhat.com> 1.0-2
- Removing old build.xml
- Fixing path def in solenopsis script
* Fri Mar 11 2011 Scot Floess <sfloess@redhat.com> 1.0-1
- Copying meta.xml files when performing a file-push, git-push or git-destructive-push
* Tue Mar 08 2011 Patrick Connelly <pconnell@redhat.com> 1.0-0
- Initial rpm packaging