from fastapi import FastAPI, File, UploadFile
import cv2
import numpy as np
from pipeline_upload_foto import upload_foto
from pipeline_upload_foto import select_skin

app = FastAPI()


@app.post("/pose_estimation/")
async def pose_estimation(id_pessoa: str, file: UploadFile = File(...)):
    print("id_pessoa:", id_pessoa, "   file.filename:", file.filename)
    contents = await file.read()
    nparr = np.fromstring(contents, np.uint8)
    img = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
    upload_foto(img, id_pessoa)
    return "termino nao sei como ..."
    
    

@app.get("/match/")
def read_item(id_pessoa: str, id_roupa: str):
    print("id_pessoa:", id_pessoa, "   id_foto:", id_foto)
    select_skin(id_pessoa, id_roupa)
    return "termino nao sei como ..."
