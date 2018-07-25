Summary: A set of scripts to help aid in Salesforce.com development and deployment
Name: solenopsis
Version:  1.2
Release: 125
URL: http://solenopsis.org/Solenopsis/
License: GPL
Group: Applications/Internet
Source0: %{name}-%{version}.tar.gz
BuildRoot: %{_tmppath}/%{name}-%{version}-%{release}-root
BuildArch: noarch
Requires: python
Requires: python-setuptools
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
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant/lib/1.9.6
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant/1.1/lib
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant/1.1/properties
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant/1.1/templates
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant/1.1/util
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant/1.2/bsh
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant/1.2/lib
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant/1.2/properties
%{__mkdir_p} %{buildroot}/usr/share/%{name}/ant/1.2/xslt
%{__mkdir_p} %{buildroot}/usr/share/%{name}/scripts
%{__mkdir_p} %{buildroot}/usr/share/%{name}/scripts/lib
%{__mkdir_p} %{buildroot}/usr/share/%{name}/scripts/templates
%{__mkdir_p} %{buildroot}/usr/share/%{name}/xsl
%{__mkdir_p} %{buildroot}/usr/share/%{name}/xsl/templates

%{__install} -p -m 0755 config/defaults.cfg %{buildroot}/usr/share/%{name}/config/
%{__install} -p -m 0755 docs/* %{buildroot}/usr/share/%{name}/docs/
%{__install} -p -m 0755 ant/lib/1.9.6/* %{buildroot}/usr/share/%{name}/ant/lib/1.9.6/
%{__install} -p -m 0755 ant/solenopsis.xml %{buildroot}/usr/share/%{name}/ant/
%{__install} -p -m 0755 ant/1.1/solenopsis-build.xml %{buildroot}/usr/share/%{name}/ant/1.1/
%{__install} -p -m 0755 ant/1.1/solenopsis-setup.xml %{buildroot}/usr/share/%{name}/ant/1.1/
%{__install} -p -m 0755 ant/1.1/lib/*.jar %{buildroot}/usr/share/%{name}/ant/1.1/lib/
%{__install} -p -m 0755 ant/1.1/properties/* %{buildroot}/usr/share/%{name}/ant/1.1/properties/
%{__install} -p -m 0755 ant/1.1/templates/* %{buildroot}/usr/share/%{name}/ant/1.1/templates/
%{__install} -p -m 0755 ant/1.1/util/* %{buildroot}/usr/share/%{name}/ant/1.1/util/
%{__install} -p -m 0755 ant/1.2/*.xml %{buildroot}/usr/share/%{name}/ant/1.2/
%{__install} -p -m 0755 ant/1.2/lib/*.jar %{buildroot}/usr/share/%{name}/ant/1.2/lib/
%{__install} -p -m 0755 scripts/solenopsis %{buildroot}/usr/share/%{name}/scripts/
%{__install} -p -m 0755 scripts/bsolenopsis %{buildroot}/usr/share/%{name}/scripts/
%{__install} -p -m 0755 scripts/lib/* %{buildroot}/usr/share/%{name}/scripts/lib/
%{__install} -p -m 0755 scripts/templates/* %{buildroot}/usr/share/%{name}/scripts/templates/
%{__install} -p -m 0755 scripts/solenopsis-completion.bash %{buildroot}/usr/share/%{name}/scripts/
%{__install} -p -m 0755 scripts/solenopsis-profile.sh %{buildroot}/usr/share/%{name}/scripts/
%{__install} -p -m 0755 xsl/templates/* %{buildroot}/usr/share/%{name}/xsl/templates/

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
echo "solenopsis.release.VERSION=%{version}-%{release}" > /usr/share/%{name}/ant/solenopsis-release.properties
easy_install argparse

%preun
rm -f /usr/bin/solenopsis
rm -f /etc/bash_completion.d/solenopsis-completion.bash
rm -f /etc/profile.d/solenopsis-profile.sh
rm -rf /usr/share/%{name}/ant/solenopsis-release.properties

%clean
rm -rf %{buildroot}

%files
%attr(0755, root, root) /usr/share/%{name}/*

%changelog
* Wed Jul 25 2018 Solenopsis <no-reply@solenopsis.org> 1.2-125
- Patrick Connelly <pconnell@redhat.com>  Fixing typo in QuickTextAction
* Fri Jul 20 2018 Solenopsis <no-reply@solenopsis.org> 1.2-124
- Patrick Connelly <pconnell@redhat.com>  Adding CustomMetadata support
* Mon Jul 16 2018 Solenopsis <no-reply@solenopsis.org> 1.2-123
- Scot P. Floess <sfloess@redhat.com>  Revert "Resolves #263 - properties defined in the credentials file are now honored.  Fixed especially for jhird!"
* Fri Jul 13 2018 Solenopsis <no-reply@solenopsis.org> 1.2-122
- Scot P. Floess <sfloess@redhat.com>  Resolves #263 - properties defined in the credentials file are now honored.  Fixed especially for jhird!
* Fri Jul 13 2018 Solenopsis <no-reply@solenopsis.org> 1.2-121
- Scot P. Floess <sfloess@redhat.com>  Resolves #257 - external file can use Package or package
* Thu Jul 12 2018 Solenopsis <no-reply@solenopsis.org> 1.2-120
- GitHub <noreply@github.com>  Updating README.md to contain build status
* Thu Feb 15 2018 Solenopsis <no-reply@solenopsis.org> 1.2-119
- Scot P. Floess <sfloess@redhat.com>  NA - solely removing StandardValueSetTranslation  (case #18338125)
- Scot P. Floess <sfloess@redhat.com>  Revert "NA - temporarily removing StandardValueSet stuff (case #18338125)."
* Thu Feb 15 2018 Solenopsis <no-reply@solenopsis.org> 1.2-118
- Scot P. Floess <sfloess@redhat.com>  NA - temporarily removing StandardValueSet stuff (case #18338125).
* Mon Jan 15 2018 Solenopsis <no-reply@solenopsis.org> 1.2-117
- No changes.
* Sun Jan 07 2018 OpenShift <solenopsis@deadlypenguin.com> 1.2-116
- Scot P. Floess <sfloess@redhat.com>  Resolves #257 - Have a way to explicitly list entities to be added to the package.xml
* Tue Oct 31 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-115
- Patrick Connelly <pconnell@redhat.com>  Adding better support for flows and versioning (Fixes #218)
* Wed Oct 18 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-114
- Patrick Connelly <pconnell@redhat.com>  Adding more default objects to the object list
* Sat Aug 05 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-113
- Scot P. Floess <sfloess@redhat.com>  Resolves #243 - An empty ignoreFile causes all files to be ignored (should be fixed now).
* Wed Jul 26 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-112
- Scot P. Floess <sfloess@redhat.com>  Revert "Resolves #243 - An empty ignoreFile causes all files to be ignored."
* Tue Jul 25 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-111
- Patrick Connelly <pconnell@redhat.com>  Adding the destructiveChangesFile parameter (Resolves #177)
* Tue Jul 25 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-110
- Patrick Connelly <pconnell@redhat.com>  Adding --username, --password and --token flags (Resolves #250)
* Sun Jul 23 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-109
- Scot P. Floess <sfloess@redhat.com>  Resolves #243 - An empty ignoreFile causes all files to be ignored.
* Fri Jul 21 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-108
- Patrick Connelly <pconnell@redhat.com>  Adding license file and code of conduct
* Fri Jul 21 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-107
- Patrick Connelly <pconnell@redhat.com>  Adding support documentation and updating formatting on readme
* Thu Jul 20 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-106
- Patrick Connelly <pconnell@redhat.com>  Adding file-delete and file-destructive-push to the wrapper script.  Closes #248
* Sat Jul 15 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-105
- Scot P. Floess <sfloess@redhat.com>  Resolves #252 - Solenopsis and Fedora 26 don't play well together.  This fix also works on RHEL 7.3 and Fedora 26.
* Wed Jul 12 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-104
- Scot P. Floess <sfloess@redhat.com>  Issue #249 - better job of ensuring proerties/env file set
* Wed Jul 12 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-103
- Scot P. Floess <sfloess@redhat.com>  Resolves #249 - now supporting overrides using solenopsis.USER, solenopsis.PASSWORD and/or solenopsis.TOKEN as -D java options
* Fri Jul 07 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-102
- Patrick Connelly <pconnell@redhat.com>  Fixing typo for testlevel
* Fri Jul 07 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-101
- Patrick Connelly <pconnell@redhat.com>  Adding --testlevel to the python wrapper (Closes #247)
* Fri Jul 07 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-100
- Patrick Connelly <pconnell@redhat.com>  Adding properties file flag to python wrapper
* Fri Jul 07 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-99
- Patrick Connelly <pconnell@redhat.com>  Fixing whitespace in template files
* Fri Jul 07 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-98
- Scot P. Floess <sfloess@redhat.com>  Resolves #164 - can now define the location of the solenopsis.properties file via solenopsis.PROPERTIES
* Tue Jul 04 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-97
- Scot P. Floess <sfloess@redhat.com>  Resolves #244 - BSFException in delta-push using sf.ignoreFile or sf.packageFile...due to an NPE
* Tue Jul 04 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-96
- Scot P. Floess <sfloess@redhat.com>  Resolves #246 - trying once again to fix dirDiff
* Sun Jul 02 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-95
- Scot P. Floess <sfloess@redhat.com>  #246 - accidentally left in some extraneous System.out.println() calls
* Sun Jul 02 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-94
- Scot P. Floess <sfloess@redhat.com>  Resolves #245 - can now specify a testLeve via sf.testLevel
* Sun Jul 02 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-93
- Scot P. Floess <sfloess@redhat.com>  Resolves #246 - dirDiff is comparing files replacing all special characters.
* Sun Jul 02 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-92
- Scot P. Floess <sfloess@redhat.com>  Resolves #211 - When doing a 'quick' deploy that doesn't change any classes, the deploy fails.
* Wed Apr 12 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-91
- Patrick Connelly <pconnell@redhat.com>  Removing problematic StandardValueSetTranslation for now
* Fri Apr 07 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-90
- Patrick Connelly <pconnell@redhat.com>  Updating to load config from the 'new' environments file
* Wed Mar 22 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-89
- Patrick Connelly <pconnell@redhat.com>  Updating defaults to 39.0
* Wed Feb 08 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-88
- Patrick Connelly <pconnell@redhat.com>  Adding entitlement process and milestone types
* Sat Jan 21 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-87
- No changes.
* Sat Jan 21 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-86
- No changes.
* Wed Jan 18 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-85
- No changes.
* Sat Jan 14 2017 OpenShift <solenopsis@deadlypenguin.com> 1.2-84
- Scot P. Floess <sfloess@redhat.com>  Resolves #237 - adding more information when installing
* Thu Dec 15 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-83
- GitHub <noreply@github.com>  Merge pull request #235 from KThompso/234-git_protocol
- Kyle Thompson-Bass <kylethompsonbass@gmail.com>  Resolves #234 - Set default git protocol to https
* Thu Dec 15 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-82
- GitHub <noreply@github.com>  Merge pull request #236 from KThompso/uname-fix
- Kyle Thompson-Bass <kylethompsonbass@gmail.com>  Simplify call to uname(1)
* Tue Dec 06 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-81
- GitHub <noreply@github.com>  Merge pull request #233 from tdalbo92/master
- Tom Dalbo <tdalbo@redhat.com>  Added installing with homebrew support
* Thu Dec 01 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-80
- Scot P. Floess <sfloess@redhat.com>  Resolves #230 - XSLTs applied on pull-to-master
* Thu Dec 01 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-79
- GitHub <noreply@github.com>  Merge pull request #232 from KThompso/231
- Kyle Thompson-Bass <kylethompsonbass@gmail.com>  Resolves #231 - Improve error message on write error
* Fri Nov 18 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-78
- Patrick Connelly <pconnell@redhat.com>  Merge branch 'master' of github.com:solenopsis/Solenopsis
- Patrick Connelly <pconnell@redhat.com>  Adding standard value picklists
* Fri Nov 18 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-77
- Patrick Connelly <pconnell@redhat.com>  Adding support for standard value set
* Thu Nov 10 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-76
- Patrick Connelly <pconnell@redhat.com>  Updating to base of api 38
* Thu Nov 10 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-75
- Patrick Connelly <pconnell@redhat.com>  Updating to address the change for api v38.0 for global picklist metadata
* Mon Sep 26 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-74
- GitHub <noreply@github.com>  Merge pull request #227 from dvf1976/globalPicklistSupportedAsMetadataType
- Daniel Fisher <dan@askingforthirds.org>  GlobalPicklist will be supported as Metadata Type
* Fri Sep 16 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-73
- Scot P. Floess <sfloess@redhat.com>  Resolves #222 - now have capability to utilize an sf.env that can override what is found in $HOME/solenopsis.properties.
* Tue Aug 02 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-72
- Patrick Connelly <pconnell@redhat.com>  Bumping the default version to 37.0
* Tue Aug 02 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-71
- Scot P. Floess <sfloess@redhat.com>  Resolves #205 - delta push will now allow for AI versions updates of metadata files when those are the only changes.
* Mon Aug 01 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-70
- Patrick Connelly <pconnell@redhat.com>  Fixing issue where escaping would happen calling the ant script - Fixes #225
* Sat Jul 09 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-69
- Scot P. Floess <sfloess@redhat.com>  Resolves #223 - need to support TLS 1.1 and 1.2 in bsolenopsis
* Thu Jul 07 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-68
- Patrick Connelly <pconnell@redhat.com>  Fixing issue where using -l flag would not work with file-push
* Wed Jul 06 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-67
- Patrick Connelly <patrick@deadlypenguin.com>  Updating java to include the TLS1.1 and TLS1.2 protocol
* Thu May 05 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-66
- Patrick Connelly <patrick@deadlypenguin.com>  Merge pull request #217 from jotraverso/patch-2
- Jorge <jotraverso@gmail.com>  Adding proxy support to build file
* Thu Apr 21 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-65
- Patrick Connelly <patrick@deadlypenguin.com>  Merge pull request #216 from jotraverso/patch-1
- Jorge <jotraverso@gmail.com>  Use cygpath to convert paths
* Tue Mar 22 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-64
- Patrick Connelly <pconnell@redhat.com>  Using apache for xsls by default then falling back to saxon for failures
* Sat Mar 19 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-63
- Scot P. Floess <sfloess@redhat.com>  Resolves #210 - provides for XSLT 2.0 and XPath 3.0
* Tue Mar 08 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-62
- Scot P. Floess <sfloess@redhat.com>  Resolves #208 - can now have XSLs denoted for pushes or pulls
* Tue Jan 12 2016 OpenShift <solenopsis@deadlypenguin.com> 1.2-61
- Scot P. Floess <sfloess@redhat.com>  Resolves #207 - ensures solenopsis.env.@{dependentEnv}.HOME is defined before using.
* Tue Nov 10 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-60
- Scot P. Floess <sfloess@redhat.com>  Resolves #204 - can now handle entity definitions when transforming.
* Tue Sep 29 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-59
- Patrick Connelly <pconnell@redhat.com>  Adding CallCenters to the metadata
* Fri Aug 28 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-58
- Scot P. Floess <sfloess@redhat.com>  Fixes #201 - but used a regular expression vs an xslt
* Fri Aug 28 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-57
- Scot P. Floess <sfloess@redhat.com>  Fixes #200 - if a trailing slash exists for the xslt dir, it chops it off.
* Thu Aug 27 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-56
- Scot P. Floess <sfloess@redhat.com>  Fixes #202 - added some defensive checks so we never get an ArrayIndexOutOfBoundsException (logging WARNINGS now).
* Tue Aug 25 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-55
- Scot P. Floess <sfloess@redhat.com>  Fixes #199 - was not considering field level changes if there were no files to be removed on file-push
* Tue Aug 25 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-54
- Patrick Connelly <pconnell@redhat.com>  Adding --xsldir to wrapper script and bumping default api version to 34.0.  Resolves #191
* Mon Aug 17 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-53
- Scot P. Floess <sfloess@redhat.com>  Fixes #192
* Mon Aug 10 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-52
- Scot P. Floess <sfloess@redhat.com>  Fixes issue #197 - performs field level diffs.
* Wed Aug 05 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-51
- Scot P. floess <sfloess@nc.rr.com>  Fixes #184 - can now use with cygwin.
* Wed Aug 05 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-50
- Scot P. Floess <sfloess@redhat.com>  Fixes #195 - now generating the XML with appropriate classpath plus a fix to the testLevel when classes are specified or not
* Wed Aug 05 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-49
- Scot P. Floess <sfloess@redhat.com>  Fixes #193 - now ensuring we process all files (even ones we are not expecting - which will yield a warning vs an NPE)
* Mon Aug 03 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-48
- Scot P. Floess <sfloess@redhat.com>  Fixes #190 - moved to ant 1.9.6
* Mon Aug 03 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-47
- Scot P. Floess <sfloess@redhat.com>  Fixes #182 - if one has XSLTs those can be applied per file (now using the sf.xslDir option).
* Sat Aug 01 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-46
- Scot P. Floess <sfloess@redhat.com>  Fixes #189.
* Wed Jul 29 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-45
- Scot P. Floess <sfloess@redhat.com>  Issue #187 - now honoring new API 34.0+ for run tests.
* Wed Jul 29 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-44
- Patrick Connelly <pconnell@redhat.com>  Adding dump-files and show-passwords flags to the solenopsis wrapper script
* Mon Jul 20 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-43
- Scot P. Floess <sfloess@redhat.com>  Issue #183 - if using API version 34.0 or greater add a testLevel attribute when running tests.
* Thu Jul 09 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-42
- Patrick Connelly <pconnell@redhat.com>  Fixing issue where file-push was using sf.version instead of file-push.VERSION
* Thu Jul 09 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-41
- Scot P. Floess <sfloess@redhat.com>  Issue #163 - fixes bad delta push issues
* Sat Jun 06 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-40
- Scot P. Floess <sfloess@redhat.com>  Issue #180 - now if one defines -Dsf.dumpFiles a build.xml is generated.  If -Dsf.showPasswords is also defined, the password will be in the build.xml.
- Scot P. Floess <sfloess@redhat.com>  Issue-179 - moving to Ant 1.9.5
* Sun May 31 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-39
- Scot P. Floess <sfloess@redhat.com>  Issue #176 - now able to specify files with spaces on the command line via -Dsf.files2push.
* Sun May 31 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-38
- Scot P. Floess <sfloess@redhat.com>  NA - fixes what is breaks when there are spaces in a -D
* Sat May 30 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-37
- Scot P. Floess <sfloess@redhat.com>  Issue #175 - can now supply destruciveChanges.xml file name using -Dsf.destructiveChangesFile
* Thu May 21 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-36
- Scot P. Floess <sfloess@redhat.com>  Issue #173 - bsolenopsis will now using JAVA_OPTS if set
* Fri Apr 24 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-35
- Scot P. Floess <sfloess@nc.rr.com>  Merge pull request #167 from jeremyross/issue166
- Jeremy Ross <jeremy@jeremyross.org>  Issue #166. Fix for hardcoded symlink location.
* Thu Apr 23 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-34
- Scot P. Floess <sfloess@redhat.com>  Issue #166 - no longer using "readlink -f" and instead looking if bsolenopsis is a symlink and acting appropriately.
* Tue Mar 24 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-33
- Patrick Connelly <patrick@deadlypenguin.com>  Adding a flag to set the ant file to use
* Tue Mar 17 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-32
- Patrick Connelly <patrick@deadlypenguin.com>  Bumping the default api version to 33
- Patrick Connelly <patrick@deadlypenguin.com>  Issue #159 - Adding the --fast option to the solenopsis wrapper
* Mon Mar 02 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-31
- Scot P. Floess <sfloess@redhat.com>  NA - incorrectly building the tests to run...was not using the delimiter presented to executeRunTest
* Sun Mar 01 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-30
- Scot P. Floess <sfloess@redhat.com>  Issue #151 - now have fast deploy.  Must use sf.fastDeploy=[anything].
* Wed Feb 11 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-29
- Scot P. floess <sfloess@nc.rr.com>  Issue #157 - bsolenopsis uses the ant libraries relative to itself (be it in /usr/share/solenopsis or in git).
* Tue Feb 03 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-28
- Scot P. Floess <sfloess@redhat.com>  Issue #146 - can now trim trailing whitespace from the ignore file clean-ignore-file target.
* Tue Feb 03 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-27
- Scot P. Floess <sfloess@redhat.com>  Issue #149 - processing contained files found in the resultant zip file was completely wrong.  Ignore file will be honored if provided.
* Tue Feb 03 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-26
- Scot P. Floess <sfloess@redhat.com>  Issue #115 - moved to Ant 1.9.4.
* Tue Feb 03 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-25
- Scot P. Floess <sfloess@redhat.com>  Issue #143 - can now purge references denoted in the sfdcignore file from permissionsets and profiles.
- Patrick Connelly <patrick@deadlypenguin.com>  Fixing typo in metadata properties
* Tue Jan 20 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-24
- No changes.
* Tue Jan 20 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-23
- No changes.
* Tue Jan 20 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-22
- No changes.
* Tue Jan 20 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-21
- No changes.
* Tue Jan 20 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-20
- No changes.
* Tue Jan 20 2015 OpenShift <solenopsis@deadlypenguin.com> 1.2-19
- Patrick Connelly <patrick@deadlypenguin.com>  Issue #131 - Adding delta-push and cache-delta-push to Python wrapper
* Mon Jan 19 2015 OpenShift <jenkins@jenkins-camponotus.rhcloud.com> 1.2-18
- Patrick Connelly <patrick@deadlypenguin.com>  Issue #148 - Adding --dryrun to the python script
* Fri Jan 02 2015 OpenShift <jenkins@jenkins-camponotus.rhcloud.com> 1.2-17
- Scot P. Floess <sfloess@redhat.com>  Issue #141 - delta pushes now support destructive changes.
* Wed Dec 31 2014 OpenShift <jenkins@jenkins-camponotus.rhcloud.com> 1.2-16
- No changes.
* Fri Oct 31 2014 Scot P. Floess <flossware@gmail.com> 1.2-15
- Issue #142 - now using the dev environment value in the computation of the temp dir.
* Tue Oct 21 2014 Scot P. Floess <flossware@gmail.com> 1.2-14
- Fixed the install.sh script to better account for OSX.
* Thu Oct 16 2014 Patrick Connelly <patrick@deadlypenguin.com> 1.2-13
- Updating for pull request to fix packageFile typo
* Mon Jun 30 2014 Scot P. Floess <flossware@gmail.com> 1.2-12
- Issue #129 - can now do automagic delta pushes of new or modified files.  This includes both a full-pull plus cached delta pushes.
- Issue #130 - if sf.dryRun is set to anything, no deploy will happen.
* Mon Jun 30 2014 Scot P. Floess <flossware@gmail.com> 1.2-11
- Incorrectly left a fail in place (issue #128).
* Sat Jun 28 2014 Patrick Connelly <patrick@deadlypenguin.com> 1.2-10
- Fixing mislabeled CustomLabel (issue 127)
* Sat Jun 28 2014 Scot P. Floess <flossware@gmail.com> 1.2-9
- Now honoring version property if set in credentials when performing destructive-pushes (issue 126).
* Thu Jun 12 2014 Patrick Connelly <patrick@deadlypenguin.com> 1.2-8
- Fixing issue with spaces and file-push
* Sat Apr 26 2014 Scot P. Floess <flossware@gmail.com> 1.2-7
- Ensuring that if one performs a file push, if the file to be pushed is included in the .sfdcignore file, we fail (issue 124).
* Thu Apr 17 2014 Patrick Connelly <patrick@deadlypenguin.com> 1.2-6
- Updating versions to support and default to api version 30.0
* Thu Nov 14 2013 Scot P. Floess <flossware@gmail.com> 1.2-5
- Added escalationRules and assignedRules (issue 122).
* Fri Jun 28 2013 Scot P. Floess <flossware@gmail.com> 1.2-4
- Any type of file push should support variable substitution (issue 114).
* Fri Jun 28 2013 Scot P. Floess <flossware@gmail.com> 1.2-3
- Now using all credentials properties as seeds for replacement before a push (issue 113).
* Sun May 26 2013 Scot P. Floess <flossware@gmail.com> 1.2-2
- Replacing Ant 1.9.0 with 1.9.1
* Thu Apr 18 2013 Patrick Connelly <patrick@deadlypenguin.com> 1.2-1
- Adding Selective pull and selective pull to master (Issue 97)
- Adding batchsize and types (Issue 97)
- Adding packagefile (issue 96)
* Sun Apr 14 2013 Scot P. Floess <flossware@gmail.com> 1.2-0
- Finalized how properties work, how they are set and interact with either Solenopsis or SFDC layers.
* Sun Mar 31 2013 Scot P. Floess <flossware@gmail.com> 1.1-62
- Incorporating a usable initial version of 1.2.  Ccan now describe metadata.
* Fri Mar 22 2013 Scot P. Floess <flossware@gmail.com> 1.1-61
- Only issuing warnings when files are included that do not have an appropriate type.
* Wed Mar 20 2013 Scot P. Floess <flossware@gmail.com> 1.1-60
- Only exceptions allowed on selective-pulls are for invalid types - which we will emit a warning and continue processing.
* Wed Mar 20 2013 Scot P. Floess <flossware@gmail.com> 1.1-59
- Using try/catch on selective pulls just in case a type is not supported.
* Tue Mar 19 2013 Scot P. Floess <flossware@gmail.com> 1.1-58
- Can now perform selective pulls based on metadata types.
* Tue Mar 19 2013 Scot P. Floess <flossware@gmail.com> 1.1-57
- If the property sf.packageFile is defined, will use that file as the package.xml vs generating it.
* Sat Mar 16 2013 Scot P. Floess <flossware@gmail.com> 1.1-56
- If the property solenopsis.release.VERSION is not set, then N/A is used.
* Sat Mar 16 2013 Scot P. Floess <flossware@gmail.com> 1.1-55
- The solenopsis version property changed - need a fix in the python script.
* Sat Mar 16 2013 Scot P. Floess <flossware@gmail.com> 1.1-54
- Emitting both the solenopsis version and release version in info task.  Additionally, if buildrpm.sh is executed outside the solenopsis tree, it will build the rpm instead of failing.
* Sat Mar 16 2013 Scot P. Floess <flossware@gmail.com> 1.1-53
- The solenopsis version is put into a properties file upon release.
* Sat Mar 16 2013 Scot P. Floess <flossware@gmail.com> 1.1-52
- Now the tmp dir is using /tmp/[USER]/solenopsis/1.1 vs /tmp/[USER]/solenopsis.
* Fri Mar 15 2013 Scot P. Floess <flossware@gmail.com> 1.1-51
- Variable substitution is slightly broken - implicit was not useable.  This is now changed.  Also added allowing one to change senderAddress if its defined.
* Tue Mar 12 2013 Scot P. Floess <flossware@gmail.com> 1.1-50
- Somehow cached-destructive-push was removed from Ant.
* Tue Mar 12 2013 Scot P. Floess <flossware@gmail.com> 1.1-49
- If package.xml was included on a file push, the push fails.
* Mon Mar 11 2013 Scot P. Floess <flossware@gmail.com> 1.1-48
- Including Ant 1.9.0
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
* Thu Jan 10 2013 Scot P. Floess <flossware@gmail.com> 1.1-23
- No longer shelling to compute diffs.  Instead we load the files as properties and then compare to see if the properties are not equal.
* Thu Jan 10 2013 Scot P. Floess <flossware@gmail.com> 1.1-22
- In newer versions of Ant (sometime after 1.7.1), file lists that contain file child elements that do not point to existing files generates a warning and Ant subsequently ahlts.  Moved over to using filesets.  Additionally changed the URL in the spec file to be the solenopsis page at github.
* Tue Jan 8 2013 Scot P. Floess <flossware@gmail.com> 1.1-21
- Newer versions of git place quotes around files with spaces (when doing git status -s).  Also had been using permissionsets when computing diffs for fields which added tons of time to compute diffs.
* Wed Dec 19 2012 Scot P. Floess <flossware@gmail.com> 1.1-20
- Using ignore file when pulling from a sandbox.  Using this so anything we will ignore on a push we equally ignore on a pull (full or to master).
* Mon Jun 11 2012 Scot P. Floess <flossware@gmail.com> 1.1-19
- Needed to do variable substitution before zipping.
* Mon Jun 11 2012 Scot P. Floess <flossware@gmail.com> 1.1-18
- Using @{varName} for our variable substitution.
* Mon Jun 11 2012 Scot P. Floess <flossware@gmail.com> 1.1-17
- Had to downgrade a regexp task for support of Ant 1.7.0
* Mon Jun 11 2012 Scot P. Floess <flossware@gmail.com> 1.1-16
- Now can do variable substitution on files pre packaging for deployment.
* Thu Jun 7 2012 Scot P. Floess <flossware@gmail.com> 1.1-15
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
