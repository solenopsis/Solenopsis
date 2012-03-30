#bash completion for solenopsis

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
	local cmds=(destructive-push push git-destructive-push git-push pull pull-to-master pull-full pull-full-to-master create describe-metadata list-metadata query help)

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
					COMPREPLY=( $( compgen -W 'class trigger page' -- "$cur" ) )
					;;
				class|trigger|page)
					COMPREPLY=( $( compgen -f -o plusdirs -- "$cur" ) )
					;;
			esac
			return 0;
			;;
		destructive-push|push|git-destructive-push|git-push|pull|pull-to-master|pull-full|pull-full-to-master|create|help)
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
