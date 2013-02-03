@echo Pushing changes to the wiki.
@echo This assumes you have git installed on your system and in PATH (executing the command 'git' in console writes the possible git operations).
@echo If anything goes wrong, please use the command line to execute commands in this file.
@echo Working in:
@cd
@echo Press any key to continue
@pause
@git add .
@git commit -m 'updated wiki'
@git status