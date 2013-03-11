Summary: A set of scripts to help aid in Salesforce.com development and deployment
Name: solenopsis
Version:  1.1
Release: 47
URL: http://solenopsis.org/Solenopsis/
License: GPL
Group: Applications/Internet
Source0: %{name}-%{version}.tar.gz
BuildRoot: %{_tmppath}/%{name}-%{version}-%{release}-root
BuildArch: noarch
Requires: python
Requires: python-argparse
Requires: java

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
%{__mkdir_p} %{buildroot}/usr/share/%{name}/docs
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant/lib/1.8.4
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant/1.1/lib
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant/1.1/properties
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant/1.1/templates
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant/1.1/util
%{__mkdir_p} %{buildroot}/usr/share/%{name}/scripts
%{__mkdir_p} %{buildroot}/usr/share/%{name}/scripts/lib
%{__mkdir_p} %{buildroot}/usr/share/%{name}/scripts/templates

%{__install} -p -m 0755 config/defaults.cfg %{buildroot}/usr/share/%{name}/config/
%{__install} -p -m 0755 docs/* %{buildroot}/usr/share/%{name}/docs/
%{__install} -p -m 0755 ant/lib/1.8.4/* %{buildroot}/usr/share/%{name}/ant/lib/1.8.4/
%{__install} -p -m 0755 ant/solenopsis.xml %{buildroot}/usr/share/%{name}/ant/
%{__install} -p -m 0755 ant/1.1/solenopsis-build.xml %{buildroot}/usr/share/%{name}/ant/1.1/
%{__install} -p -m 0755 ant/1.1/solenopsis-setup.xml %{buildroot}/usr/share/%{name}/ant/1.1/
%{__install} -p -m 0755 ant/1.1/lib/*.jar %{buildroot}/usr/share/%{name}/ant/1.1/lib/
%{__install} -p -m 0755 ant/1.1/properties/* %{buildroot}/usr/share/%{name}/ant/1.1/properties/
%{__install} -p -m 0755 ant/1.1/templates/* %{buildroot}/usr/share/%{name}/ant/1.1/templates/
%{__install} -p -m 0755 ant/1.1/util/* %{buildroot}/usr/share/%{name}/ant/1.1/util/
%{__install} -p -m 0755 scripts/solenopsis %{buildroot}/usr/share/%{name}/scripts/
%{__install} -p -m 0755 scripts/bsolenopsis %{buildroot}/usr/share/%{name}/scripts/
%{__install} -p -m 0755 scripts/lib/* %{buildroot}/usr/share/%{name}/scripts/lib/
%{__install} -p -m 0755 scripts/templates/* %{buildroot}/usr/share/%{name}/scripts/templates/
%{__install} -p -m 0755 scripts/solenopsis-completion.bash %{buildroot}/usr/share/%{name}/scripts/
%{__install} -p -m 0755 scripts/solenopsis-profile.sh %{buildroot}/usr/share/%{name}/scripts/

%pre
rm -f /usr/bin/solenopsis
rm -f /usr/bin/bsolenopsis
rm -f /etc/bash_completion.d/solenopsis-completion.bash
rm -f /etc/profile.d/solenopsis-profile.sh

%posttrans
ln -sf /usr/share/%{name}/scripts/solenopsis /usr/bin/solenopsis
ln -sf /usr/share/%{name}/scripts/bsolenopsis /usr/bin/bsolenopsis
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
* Sun Mar 10 2013 Scot P. Floess <flossware@gmail.com> 1.1-47
- bsolenopsis supports --antversion and --help command line options
* Sun Mar 10 2013 Scot P. Floess <flossware@gmail.com> 1.1-46
- bsolenopsis does the work of bsolenopsisant and will handle -f command line option.  Also now ensuring we don't keep around old pyc and pyo files
* Fri Mar 8 2013 Scot P. Floess <flossware@gmail.com> 1.1-45
- Python script is using df.version instead of sf.version
* Tue Mar 5 2013 Patrick Connelly <patrick@deadlypenguin.com> 1.1-44
- Adding gitshell, apiversion, solversion and pkgdir to wrapper
* Mon Mar 4 2013 Scot P. Floess <flossware@gmail.com> 1.1-43
- Upgrading to JGit 2.3.1.
* Sun Mar 3 2013 Scot P. Floess <flossware@gmail.com> 1.1-42
- Multiple Ant versions of Solenopsis can be supported.
* Sat Mar 2 2013 Scot P. Floess <flossware@gmail.com> 1.1-41
- Can now denote the property solenopsis.git-status.shell and if set, will shell into the OS for git status.
* Tue Feb 26 2013 Scot P. Floess <flossware@gmail.com> 1.1-40
- Fixing yum dependencies.
* Mon Feb 25 2013 Scot P. Floess <flossware@gmail.com> 1.1-39
- Removing git-status using JGit shelling back to the OS.
* Mon Feb 25 2013 Scot P. Floess <flossware@gmail.com> 1.1-38
- Re-introduced shelling into OS for git status.  However, this is now driven by the environment variable SOLENOPSIS_GIT_OS_SHELL.
* Sun Feb 24 2013 Scot P. Floess <flossware@gmail.com> 1.1-37
- Can now generate package.xml for folder and non-folder based operations.
* Sat Feb 23 2013 Scot P. Floess <flossware@gmail.com> 1.1-36
- Cleaned up building the XML for run tests.
* Sat Feb 23 2013 Scot P. Floess <flossware@gmail.com> 1.1-35
- Now using JGit instead of shelling out to the OS for git-status.
* Sat Feb 23 2013 Scot P. Floess <flossware@gmail.com> 1.1-34
- Including licenses and added some documentation.
* Thu Feb 21 2013 Patrick Connelly <patrick@deadlypenguin.com> 1.1-33
- Fixing typo in wrapper script with filecontains
* Thu Feb 21 2013 Scot P. Floess <flossware@gmail.com> 1.1-32
- Can now do pushes of files that contain a string.
* Sun Feb 17 2013 Scot P. Floess <flossware@gmail.com> 1.1-31
- Refactored code to use credentials file properties over any other properties (when defined) and can now run all non managed tests.
* Sat Feb 9 2013 Scot P. Floess <flossware@gmail.com> 1.1-30
- Reports were denoted incorrectly in the metadata properties file.  Also moved API to 26.0.
* Fri Feb 8 2013 Patrick Connelly <patrick@deadlypenguin.com> 1.1-29
- Adding cached-destructive-push to the python wrapper.
* Sat Feb 2 2013 Scot P. Floess <flossware@gmail.com> 1.1-28
- Can now spell out the API version and ant jar file in the users ~/solenopsis.properties files.  Additionally added more useful output for pushes.
* Wed Jan 30 2013 Scot P. Floess <flossware@gmail.com> 1.1-27
- When an ampersand exists in user name, password or token, the generated runtests.xml was not escaping correctly.
* Mon Jan 28 2013 Patrick Connelly <patrick@deadlypenguin.com> 1.1-26
- Adding hooks that were added to the ant lib
- Can now run a tests class, denote log type and use the checkOnly feature for deployments (check the deploy but don't deploy)
* Mon Jan 14 2013 Scot P. Floess <flossware@gmail.com> 1.1-24
- Removed the LiveChatButton as a custom object (its not there), and were getting annoying warnings.
* Fri Jan 11 2013 Scot P. Floess <flossware@gmail.com> 1.1-23
- Git push was broken (as was file-push).  Also am now spitting out the java/ant versions when running.
* Tue Jan 10 2013 Scot P. Floess <flossware@gmail.com> 1.1-23
- No longer shelling to compute diffs.  Instead we load the files as properties and then compare to see if the properties are not equal.
* Tue Jan 10 2013 Scot P. Floess <flossware@gmail.com> 1.1-22
- In newer versions of Ant (sometime after 1.7.1), file lists that contain file child elements that do not point to existing files generates a warning and Ant subsequently ahlts.  Moved over to using filesets.  Additionally changed the URL in the spec file to be the solenopsis page at github.
* Tue Jan 8 2013 Scot P. Floess <flossware@gmail.com> 1.1-21
- Newer versions of git place quotes around files with spaces (when doing git status -s).  Also had been using permissionsets when computing diffs for fields which added tons of time to compute diffs.
* Mon Dec 19 2012 Scot P. Floess <flossware@gmail.com> 1.1-20
- Using ignore file when pulling from a sandbox.  Using this so anything we will ignore on a push we equally ignore on a pull (full or to master).
* Mon Jun 11 2012 Scot P. Floess <flossware@gmail.com> 1.1-19
- Needed to do variable substitution before zipping.
* Mon Jun 11 2012 Scot P. Floess <flossware@gmail.com> 1.1-18
- Using @{varName} for our variable substitution.
* Mon Jun 11 2012 Scot P. Floess <flossware@gmail.com> 1.1-17
- Had to downgrade a regexp task for support of Ant 1.7.0
* Mon Jun 11 2012 Scot P. Floess <flossware@gmail.com> 1.1-16
- Now can do variable substitution on files pre packaging for deployment.
* Tue Jun 7 2012 Scot P. Floess <flossware@gmail.com> 1.1-15
- Added live chat related custom objects for package.xml
* Tue May 22 2012 Patrick Connelly <patrick@deadlypenguin.com> 1.1-14
- Adding report-diff and updating bash completion for missing commands
- Added ant to required rpms
* Tue May 22 2012 Scot P. Floess <flossware@gmail.com> 1.1-13
- When computing branches, if the root had multiple slashes at the end, caused destructive-changes to break.
- Updated unit tests
* Mon May 07 2012 Patrick Connelly <patrick@deadlypenguin.com> 1.1-12
- Adding requestId flag
* Mon May 07 2012 Patrick Connelly <patrick@deadlypenguin.com> 1.1-11
- Adding run-tests to the list of available commands
* Tue May 01 2012 Scot Floess <sfloess@nc.rr.com> 1.1-10
- Fixing issue with max poll capitalization
* Mon Apr 30 2012 Patrick Connelly <patrick@deadlypenguin.com> 1.1-9
- Adding max poll flag
- Adding temp dir flag
* Thu Apr 19 2012 Patrick Connelly <patrick@deadlypenguin.com> 1.1-8
- Adding ignore flag to set the sf.ignoreFile flag
* Tue Apr 17 2012 Patrick Connelly <patrick@deadlypenguin.com> 1.1-7
- Adding --home flag to set the local.HOME variable
- Fixing error with multiple flags
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