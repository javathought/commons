#!/usr/bin/env bash
if ([ "$TRAVIS_BRANCH" = 'master' ] || [ "$TRAVIS_BRANCH" = 'develop' ]) && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    mvn deploy -e -P sign,build-extras --settings .travis/mvnsettings.xml
fi
