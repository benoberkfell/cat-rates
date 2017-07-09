# CatRates

## About
This is a demo app for my conference talk, "Android Architecture For The Subway"

It takes a feed of cat pictures from [thecatapi.com](thecatapi.com) and renders them.  If one of these cats ranks highly 
in your book, you can also save it to keep your favorite cats close at hand.  Just long-press on her/him.

Want a new set of cats? Pull to refresh.

## Core Features

This seeks to demo a few important concepts:

**Caching.** Illustrating a use of a backing cache for persisting the cat picture stream.  The stream is pulled down and 
rendered, and then the data is just thrown away on refresh, so there's not a lot of benefit to persist individual 
pictures just for the sake of caching them.

So, we use the [Store](https://github.com/NYTimes/Store) library to handle fetching and persisting the cache for us.

The Store fetches the response from the server, caches it locally, and pushes it through the parser to return data 
model objects we can use to render.  If we want the feed again, Store gives it to us from the cache.

This app is also using Picasso to hang onto images after rendering them.

**Database Persistence.** The app uses Room to persist selected images off the stream you'd like to keep.  While 
the Store is great for the raw feed, when we want to save a cat to keep, we're best off actually creating a local 
database record for it.

**Preloading Data.** Suppose you downloaded this app, and then while bored on the subway, you remembered you 
had never opened this great cat app that had a million five-star reviews.  Wouldn't you be disappointed to open it 
and find no cats, because you didn't have network access?

Check out the `Application.onCreate()` where on your first launch we warm up the cache with your first set of cats,
by copying a response into where the Store keeps its data.  The initial response refers to some images in the 
assets directory, so you'll see all the cats in their full glory.  

You can even still adopt a cat by long-pressing.
