#!/usr/bin/env sh

# Requirements:
# - sh
# - docker
# - maven

mvn spring-boot:build-image -Dspring-boot.build-image.imageName=maryjein/bestseller:latest

