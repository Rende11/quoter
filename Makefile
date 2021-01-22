.EXPORT_ALL_VARIABLES:

IMG_NAME=rende11/quoter
IMG_TAG=0.0.1
IMG=${IMG_NAME}:${IMG_TAG}

install:
	npm install

dev:
	clj -M:shadow:dev watch app

report:
	clj -M:report app report.html

build-ui:
	clj -M:shadow release app

build-image:
	rm -rf public/js/cljs-runtime
	docker build . -t ${IMG}

run:
	docker run -it -p 3080:80 ${IMG}

pub:
	docker push ${IMG}

all: build-ui build-image pub

