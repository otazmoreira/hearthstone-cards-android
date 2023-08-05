#!/bin/bash

echo "Removing pre-commit.sample file from .git"
rm ../.git/hooks/pre-commit.sample
echo "pre-commit.sample file removed!"

echo "Creating new pre-commit file on .git"
cp -r pre-commit ../.git/hooks/

echo "The pre-commit file was created!"
echo "Finished!"
