#bash completion for solenopsis

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


_get_envs() {
	local envs=$(for file in `ls ~/.solenopsis/credentials/`; do echo ${file} | awk -F. '{print $1}'; done)
	COMPREPLY=( $( compgen -W "${envs}" -- "$cur" ) )
}

_solenopsis() {
	COMPREPLY=()
	local cur
	local sol=$1
	type _get_cword &>/dev/null && cur=`_get_cword` || cur=$2
	local prev=$3
	local cmds=(destructive-push push cached-destructive-push git-destructive-push git-push pull pull-to-master pull-full pull-full-to-master create describe-metadata list-metadata run-tests query help file-push report-diff generate-package generate-full-package selective-pull selective-pull-to-master file-delete file-destructive-push)

	for (( i=0; i < ${#COMP_WORDS[@]}-1; i++ )) ; do
		for c in ${cmds[@]} ; do
			[ ${COMP_WORDS[i]} = $c ] && cmd=$c && break
		done
		[ -z $cmd ] || break
	done

	case $cmd in
		create)
			case $prev in
				create)
					COMPREPLY=( $( compgen -W 'class trigger page test webservice class-trigger' -- "$cur" ) )
					;;
				class|trigger|page)
					COMPREPLY=( $( compgen -f -o plusdirs -- "$cur" ) )
					;;
			esac
			return 0;
			;;
		destructive-push|push|git-destructive-push|git-push|pull|pull-to-master|pull-full|pull-full-to-master|create|help|report-diff|file-push|generate-package|generate-full-packageselective-pull|selective-pull-to-master|file-delete|file-destructive-push)
			return 0;
			;;
	esac

	local split=false
	type _split_longopt &>/dev/null && _split_longopt && split=true

	case $prev in
		-e|-m)
			_get_envs
			return 0;
			;;
		-s)
			COMPREPLY=( $( compgen -f -o plusdirs -- "$cur" ) )
			return 0;
			;;
	esac

	COMPREPLY=( $( compgen -W '-v -d -f -a -s -t -e -m ${cmds[@]}' -- "$cur" ) )
} &&
complete -F _solenopsis solenopsis