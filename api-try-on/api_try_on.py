from fastapi import FastAPI, File, UploadFile
import cv2
import numpy as np
from pipeline_upload_foto import upload_foto
from pipeline_upload_foto import select_skin
import os
import base64
from pydantic import BaseModel

app = FastAPI()

class Item(BaseModel):
    id_pessoa: str
    image: str


@app.post("/pose_estimation/")
def pose_estimation(item: Item):
    image = item.image[item.image.find('base64,')+7::]
    id_pessoa = item.id_pessoa
    print("id_pessoa:", id_pessoa, image[0:10])
    decoded_data = base64.b64decode(image)
    np_data = np.fromstring(decoded_data,np.uint8)
    img = cv2.imdecode(np_data,cv2.IMREAD_COLOR)
    upload_foto(img, id_pessoa)
    return "termino nao sei como ..."
    
    

@app.get("/match/")
def read_item(id_pessoa: str, id_roupa: str):
    print("id_pessoa:", id_pessoa, "   id_foto:", id_roupa)
    select_skin(id_pessoa, id_roupa)
    img = cv2.imread(id_pessoa+"/final.png")
    retval, buffer = cv2.imencode('.png', img)
    jpg_as_text = base64.b64encode(buffer)
    return jpg_as_text

