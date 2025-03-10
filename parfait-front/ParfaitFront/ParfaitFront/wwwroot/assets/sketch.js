// ml5.js: Pose Estimation with PoseNet
// The Coding Train / Daniel Shiffman
// https://thecodingtrain.com/learning/ml5/7.1-posenet.html
// https://youtu.be/OIo-DIOkNVg
// https://editor.p5js.org/codingtrain/sketches/ULA97pJXR

let video;
let poseNet;
let pose;
let skeleton;

function setup() {
    createCanvas(640, 480);
    video = createCapture(VIDEO);
    video.hide();
    poseNet = ml5.poseNet(video, modelLoaded);
    poseNet.on('pose', gotPoses);
}

function gotPoses(poses) {
  //console.log(poses);
  if (poses.length > 0) {
    pose = poses[0].pose;
    skeleton = poses[0].skeleton;
  }
}

function modelLoaded() {
  console.log('poseNet ready');
}

function draw() {
  image(video, 0, 0);

  if (pose) {
    let eyeR = pose.rightEye;
    let eyeL = pose.leftEye;
    let d = dist(eyeR.x, eyeR.y, eyeL.x, eyeL.y);
    fill(255, 0, 0);
    ellipse(pose.nose.x, pose.nose.y, d);
    fill(0, 0, 255);
    ellipse(pose.rightWrist.x, pose.rightWrist.y, 32);
    ellipse(pose.leftWrist.x, pose.leftWrist.y, 32);

    for (let i = 0; i < pose.keypoints.length; i++) {
      let x = pose.keypoints[i].position.x;
      let y = pose.keypoints[i].position.y;
      fill(0, 255, 0);
      ellipse(x, y, 16, 16);
    }

    for (let i = 0; i < skeleton.length; i++) {
      let a = skeleton[i][0];
      let b = skeleton[i][1];
      strokeWeight(2);
      stroke(255);
      line(a.position.x, a.position.y, b.position.x, b.position.y);
    }
  }
}

function takepicture() {
    let canvasComponent = document.getElementById('defaultCanvas0');
    let myCanvas = document.getElementById('canvas');
    let context = myCanvas.getContext('2d');

    let video = document.getElementsByTagName("video")[0]

    context.drawImage(video, 0, 0, canvas.width, canvas.height);
    toggleElements()
}

function toggleCanvas() {

    $("canvas").toggleClass("hidden");

}

function toggleButtons() {
    $(".btn-div").toggleClass("hidden")
}

function toggleElements() {
    toggleCanvas()
    toggleButtons()
}

function nextFoto() {
    let myCanvas = document.getElementById('canvas');
    var dataURL = myCanvas.toDataURL();
    objectImages.push(dataURL)

    $("#resetFoto").toggleClass("hidden")
    $("#nextFoto").toggleClass("hidden")
    $("#finish").toggleClass("hidden")
    toggleElements()
    toggleMsg()
}

function finish() {
    let myCanvas = document.getElementById('canvas');
    var dataURL = myCanvas.toDataURL();
    objectImages.push(dataURL)
    sendPictures(objectImages[0], objectImages[1])

    $(".foto-component").toggleClass("hidden")
    $(".main-canvas").toggleClass("hidden")
    $(".msg").addClass("hidden")

    $(".finish").toggleClass("hidden");

}

function toggleMsg() {
    $(".msg").toggleClass("hidden")
}


