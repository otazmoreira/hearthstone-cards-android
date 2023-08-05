#!/bin/bash

echo "Create pre-commit file on .git"
cp -r pre-commit ../.git/hooks/
echo "The pre-commit file was created!"
echo "Finished!"
