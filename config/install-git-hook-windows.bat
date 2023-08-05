ECHO "Creating pre-commit file on .git"

xcopy .\pre-commit ..\.git\hooks\

ECHO "The pre-commit file was created!"

ECHO "Finished!"

PAUSE

exit