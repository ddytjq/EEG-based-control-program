## Concentration and meditation management and improvement programs using EEG

#### ‘뇌파를 활용한 집중, 명상 관리 및 개선 프로그램’은 ADHD 환자 치료 및 기존 집중력 관리 프로그램을 보완하고 내부 기능을 향상하고자 아이디어를 제시한다. 사용자의 실시간 뇌파를 통해 만든 이미지와 CNN 기반 알고리즘 Inception v3를 활용하여 사용자의 현재 상태를 정확하게 예측 후 상태 관리를 통해 최상의 명상 상태를 만들어 최대의 집중력을 발휘 하도록 도움을 주도록 구성하였다.

#### Hardware
* neurosky mindwave mobile

#### Software
* Tool : android studio , processing 3 , eclipse , colaboratory , 
* Language : java 9.0.4 , android 3.1.2 (api 24) , processing 3.3.7 , python 3.6.5
* Database : firebase

#### Function
* Mindwave Mobile : eeg data extraction (delta, theta, alpha, SMR, low beta, high beta, gamma)
* Processing 3 : eeg progress graph (Running time 10 seconds)
* Inception v3 : eeg graph matching, Status output according to matching
* Android : eeg measurement through mindwave & application gui

#### Each file
* Classification : Measured EEG and dataset normalization
* JS-master : Result Project file
* MyMy : Application User Interface
* Processing : Creating a graph image using EEG data
* Tensorflow-Android-master : Image matching - Android integration

#### Reference
* eeg data set : https://www.kaggle.com/wanghaohan/confused-eeg
* firebase : Use as database, Supported by Google
* colaboratory : Use colab's virtual gpu computer to do deep learning
* https://steemit.com/kr/@deep-root/research-tips-colaboratory-colab

#### Demo
* https://www.youtube.com/watch?v=X0F0W6UgiYQ
