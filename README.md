## MyEats

---

#### By [Summer Brochtrup](https://www.linkedin.com/in/summerbrochtrup) (2016)

---

### Description

---

<img style="float: right;margin:20px;" alt="main-activity" src="https://raw.githubusercontent.com/summerbr/myeats/master/mainactivity.png">
<img style="float: right;margin:20px;" alt="find-activity" src="https://raw.githubusercontent.com/summerbr/myeats/master/findactivity.png">
<img style="float: right;margin:20px;" alt="detail-activity" src="https://raw.githubusercontent.com/summerbr/myeats/master/detailactivity.png">
<img style="float: right;margin:20px;" alt="detail-activity2" src="https://raw.githubusercontent.com/summerbr/myeats/master/detailactivity2.png">

MyEats is a simple restaurant searching app. Users can view nearby restaurants or search for restaurants by specific location. Users can then save restaurants they intend to visit. Saved restaurants can be sorted via drag and drop to indicate the priority in which they would like to eat at them.

### To Do

---

* Currently ,MyEats is optimized for the Google Nexus 6P. Alternative layouts should be added to accommodate varying screen sizes.
* Enable users to take photos of the foods they eat at a specific restaurant and save them to a gallery.
* Enable users to share photos with other users of the app using Firebase.

### Technologies Used

---

MyEats utilizes SQLite to store restaurants. It also implements Retrofit2 to search for restaurants via the Yelp API. The UI is created using CoordinatorLayouts, CollapsingToolbars, shared element activity transitions, and alternative layouts for landscape orientation. MyEats also utilizes Google's FusedLocationAPI to locate the user and Google Maps to display the location of the restaurant to the user. User authorization is implemented with Firebase with the intention of one day enabling users to share content with all users or select users.

### Known Issues

---

The 'LastKnownLocation' feature does not display nearby restaurants the first time when a user is prompted to permit MyEats to use their current location.

### License

---

This software is licensed under the MIT license.

Copyright (c) 2016 Summer Brochtrup

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
