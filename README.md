"We have the duty to protect the life of an unborn child."
 *- Ronald Reagan*

# Quoter application 
---
Tiny CLJS app - [Quote generator (demo)](https://r-quoter.herokuapp.com/). 
Original(for me) idea - [Clojurescript - Random Quote Generator](https://www.youtube.com/watch?v=Sa-MaCmS0gk&ab_channel=KelvinMai)

My goal was write tiny ClojureScript appication and go through all the main stages of the client application development cycle at the basic level - coding([CLJS](https://clojurescript.org/)), styling(CSS, [Garden](https://github.com/noprompt/garden)), adapting to different devices(flex, media-queries), build([shadow-cljs](https://shadow-cljs.github.io/docs/UsersGuide.html)), launch(Nginx, Docker) and deploy(Heroku)

## Some tips
##### Launch locally from source
```
make install
make dev
```
Open `http://localhost:8686/`


##### Launch locally from docker image
```
make run
```
Open `http://localhost:3080/`
