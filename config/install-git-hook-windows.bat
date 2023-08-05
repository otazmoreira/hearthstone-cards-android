echo "Removing pre-commit.sample file from .git"
del ../.git/hooks/pre-commit.sample
echo "pre-commit.sample file removed!"

echo "Creating pre-commit file on .git"
xcopy .\pre-commit ..\.git\hooks\
echo "pre-commit file was created!"

echo "Making files executables"
icacls ..\.git\hooks\pre-commit /grant Everyone:RX

echo "Finished!"

pause
exit