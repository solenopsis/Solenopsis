# Solenopsis #

A commandline interface for deploying to Salesforce

Currently you will need to download the ant-salesforce.jar to the ant/lib/ directory.  We will be working on making this an automated process as well as phasing out the ant library for the MetadataAPI.

# Dependencies #
+ Python
+ Ant
+ Python Beatbox (optional)

# Installation #
## RPM ##
If you are on an RPM based system, you can run `make rpm` in the root directory and install the generated RPM
## SOURCE ##
1. Checkout the repo or extract the zip/tar
2. Symlink (or move) the repo to /usr/share/solenopsis/   `ln -s /path/to/checkout /usr/share/solenopsis/`
3. Symlink the solenopsis script into a bin directory   `ln -s /usr/share/solenopsis/scripts/solenosis /usr/local/bin/solenopsis`

# Setup #
1. Get the ant library from your salesforce instance. Click Your Name | Setup | Develop | Tools, then flick Force.com Migration Tool
2. Extract the zip file and place the _ant-salesforce.jar_ in _/usr/share/ant/lib_ or in _~/.ant/lib/_
3. Make a directory for where your source will be checked out `mkdir ~/sfdc` and use this in the next step
4. Fetch your current sandbox and run `solenopsis pull-full-to-master`

# Standard Workflow #
This workflow will keep your git repo clean and will make your life a lot easier for teams with multiple users.  We recommend that each developer have their own developer sandbox that they do their development on.  Then the code is pushed to a central full sandbox for testing.  This pushing can be done via [Jenkins](http://jenkins-ci.org/) with the aid of Solenopsis.

1. Get the most recent code from the git repo `git pull`
2. Push this code to your sandbox `solenopsis destructive-push`
3. Create a private feature branch `git checkout -b mynewfeature`
4. Do work locally and push `solenopsis push` (the command `solenopsis git-push` can be used for faster deployment of apex/VF code
5. Fetch work that was done in config from salesforce `solenopsis pull-full-to-master`
6. Commit work to private feature branch `git commit -a -m "My feature"`
7. See if there are any updates in the master branch `git checkout master` `git pull`
8. If there are changes since the last time you pulled, rebase and resolve issues `git checkout mynewfeature` `git rebase master`
9. After changes are resolved merge your code into master `git checkout master` `git merge --squash mynewfeature`
10. Commit your changes `git commit`
11. Push changes to master `git push`
12. Delete your feature branch `git branch -D mynewfeature`

# Caveats #
Currently any folder based operations (email) must be created by hand (a blank file will do) in order to be able to fetch them.

##Fetch Email Template Example##
We are currently working on a better way to do this

_This is all relative to your SFDC src directory_

1. Make the email directory `mkdir email`
2. Make the folder the email is in (this name is determined by SFDC, just look at the API name) `mkdir email/Case_Templates_internal`
3. Make the metadata file `touch email/Case_Templates_internal-meta.xml`
4. Make one of the emails in the folder (again the API name) `touch email/Case_Templates_internal/Case_Closed_internal.email`
5. Make the metadata file for it `touch email/Case_Templates_internal/Case_Closed_internal.email-meta.xml`
6. Pull the data from salesforce `solenopsis pull-full-to-master`

_For unfiled emails make the folder `mkdir email/unfiled\$public`_

_NOTE: Currently this project has only been developed and tested on Linux.  Patches and help for other platforms are always accepted._