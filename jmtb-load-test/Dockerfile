FROM grafana/k6

WORKDIR /load-test

COPY *.js .

ENV TEST_FILE 'load-test-1.js'

ENTRYPOINT k6 run $TEST_FILE