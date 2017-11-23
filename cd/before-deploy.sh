#!/usr/bin/env bash
if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    openssl aes-256-cbc -K $encrypted_a4fbf60a1a6d_key -iv $encrypted_a4fbf60a1a6d_iv -in cd/codesigning.asc.enc -out cd/codesigning.asc -d
    gpg --batch --fast-import cd/codesigning.asc
fi