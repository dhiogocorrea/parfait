from PIL import Image
import os
import matplotlib.pyplot as plt
import numpy as np

import cv2
from keras.applications import vgg16
from keras.preprocessing.image import load_img,img_to_array
from keras.models import Model
from keras.models import load_model
from keras.applications.imagenet_utils import preprocess_input

from sklearn.metrics.pairwise import cosine_similarity
import pandas as pd

import requests


def main():

    files = requests.get('http://169.62.157.212:1992/product')

    if files.status_code != 200:
        print(files.status_code)
    
    filesJson = files.json()

    #print(filesJson.images)

    vgg_model = vgg16.VGG16(weights='imagenet')

    feat_extractor = Model(inputs=vgg_model.input, outputs=vgg_model.get_layer("fc2").output)

    importedImages = []
    for f in filesJson: 
        url = ""
        for image in f["images"]:
            if(image["target"]):
                url = image["smallImageUrl"]

        if(url != ""):

            old_file_name = url.split("/")[-1]
        
            extension = "."+old_file_name.split(".")[-1]
            pd_id = f["productId"]
            r = requests.get("https:"+url, allow_redirects=True)
            filename = 'tumbs/'+ pd_id
            open(filename + extension, 'wb').write(r.content)
        
            file_path = filename + extension
            mask_path = 'tumbMasks/'+ pd_id + ".png"
            file_png = filename + ".png"

            img = cv2.imread(file_path, 0)

            _,thresh1 = cv2.threshold(img,200,255,cv2.THRESH_BINARY_INV)

            kernel = np.ones((5,5),np.uint8)

            closing = cv2.morphologyEx(thresh1, cv2.MORPH_CLOSE, kernel)

            cv2.imwrite(mask_path, closing)

            img = cv2.imread(file_path)

            cv2.imwrite(file_png, img)

            original = load_img(filename, target_size=(224, 224))
            numpy_image = img_to_array(original)
            image_batch = np.expand_dims(numpy_image, axis=0)
        
            importedImages.append(image_batch)
            os.remove(file_path)
        
    images = np.vstack(importedImages)

    processed_imgs = preprocess_input(images.copy())

    imgs_features = feat_extractor.predict(processed_imgs)

    cosSimilarities = cosine_similarity(imgs_features)
    cos_similarities_df = pd.DataFrame(cosSimilarities, columns=files, index=files)

    cos_similarities_df.to_excel("./df_recommendations.xlsx")


if __name__ == "__main__":
    main()