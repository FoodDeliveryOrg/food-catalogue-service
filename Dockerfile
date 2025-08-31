FROM ubuntu:latest
LABEL authors="shaku"

ENTRYPOINT ["top", "-b"]