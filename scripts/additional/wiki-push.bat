@echo Pushing changes to the wiki.
@echo This assumes you have git installed on your system and in PATH (executing the command 'git' in console writes the possible git operations).
@echo If anything goes wrong, please use the command line to execute commands in this file.
@echo Working in:
@cd
@echo ====================================
@echo Ready to commit...
@pause
@git add -u .
@git commit -m "updated wiki"
@git status
@echo ====================================
@echo If anything to commit, ready to push (ready to insert passphrase); otherwise close this window
@pause
@ssh-agent bash -c "ssh-add $HOME/.ssh/githubkey; git push"
@echo ====================================
@echo Supposedly done.
@pause
@exit