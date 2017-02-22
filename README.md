#Preview of the final App
![Sneak Peak into the App](https://github.com/dennyabrain/NikeAssignment/blob/master/nike-screenshot-smaller.gif)

[See High-Res Gif](https://www.dropbox.com/s/rvmljablcc9pgbm/nike-screenshot.gif?dl=0)

#Idea of the Experience
Sometimes people like jogging around their neighbourhood and it makes the whole experience feel new if you take a new track everyday. This app is a mockup of an app which detects your present location and fetches running tracks around you.
The map itself you see in the preview is a static map but the name of the place you see overlayed on top of it has been fetched using the Google Places API. In its current iteration, the app searches for art galleries around you and lists them.

#Flow
User clicks "Search Tracks", this displays a viewpager with static image files and a progressbar. In the background I detect the current location of the device using Location Services API. I send that data to the Geocoder class to get the User Friendly name of the location (eg Jersey City, New York City etc)
Upon successfully completing this, a callback is sent to the main fragment which fetches a list of Art Galleries near you within a fixed radius (I chose Art Galleries as a random selection. The API gives a list of options which could be configured), this list is then used to populate the various cards of the carousel.

I spent a lot of time animating the different views in a meaningful way so the user does not experience any janky behaviour or confusing UI while all this data is being fetched.

#Library-fication
I exposed my app's functionality as a Fragment so you can embed the whole thing into any app by pasting
```
<fragment
        android:name="space.dennymades.nikelab.NikeFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

#Caveats
1. You have to accept the location access permission at runtime. I haven't taken into consideration use cases when you don't allow those permission requests.
2. Please enable Location Access to ensure the app has something to work with.
3. I am trying to use the Geocoder API to fetch the name of your locality using the Latitude and Longitude data but I have read online that this API works inconsistently across devices, in which case Instead of, lets say your message being "showing running tracks around Jersey City", it will say "showing running tracks around N/A"

