install:
	npm install

dev:
	clj -M:shadow:dev watch app

report:
	clj -M:report app report.html

build:
	clj -M:shadow release app
