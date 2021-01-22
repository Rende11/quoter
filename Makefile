install:
	npm install

dev:
	clj -M:shadow:dev watch app

report:
	clj -M:report app report.html

build-ui:
	clj -M:shadow release app

build-image:
	docker build . -t quoter:0.0.1

run:
	docker run -it -p 3080:80 quoter:0.0.1
