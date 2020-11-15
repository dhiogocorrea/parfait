#!/usr/bin/env python3

from fastapi import FastAPI
from keras.models import load_model
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np
import os
import pandas as pd

app = FastAPI()

cos_similarities_df = pd.read_excel("../parfait-training-recommendation/df_recommendations.xlsx")
print("DataFrame ready")

@app.get("/")
def retrieve_most_similar_products(img_id, nb_closest_images):

    img_path = "tumbs/" + img_id + ".png"

    closest_imgs = cos_similarities_df[img_path].sort_values(ascending=False)[1:int(nb_closest_images)+1].index
    
    path_imgs_array = []

    for img_index in closest_imgs:
        path_imgs_array.append(cos_similarities_df[img_index].replace(".png", ""))
    
    return path_imgs_array