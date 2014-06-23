#!/bin/bash
# git remote add heroku git@heroku.com:expatmdm.git
git add -A .
git commit -m "nodejs changes"
cd ..
git subtree push --prefix nodejs heroku master
