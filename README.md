#Preview of the final App
[High-Res Gif](https://www.dropbox.com/s/rvmljablcc9pgbm/nike-screenshot.gif?dl=0)

#Idea of the Experience
Sometimes people like jogging around their neighbourhood and it makes the whole experience feel new if you take a new track everyday. This app is a mockup of an app which detects your present location and fetches running tracks around you.
The map itself you see in the preview is a static map but the name of the place you see overlayed on top of it has been fetched using the Google Places API. In its current iteration, the app searches for art galleries around you and lists them.

#Caveats
1. You have to accept the location access permission at runtime. I haven't taken into consideration use cases when you don't allow those permission requests.
2. Please enable Location Access to ensure the app has something to work with.
3. I am trying to use the Geocoder API to fetch the name of your locality using the Latitude and Longitude data but I have read online that this API works inconsistently across devices, in which case Instead of, lets say your message being "showing running tracks around Jersey City", it will say "showing running tracks around N/A"

#HighLights
