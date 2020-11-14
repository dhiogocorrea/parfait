import sys
import os
import cv2
from shutil import copyfile

def resize(img_path):
    img = cv2.imread(img_path)
    dim = (192,256)
    resized = cv2.resize(img, dim, interpolation = cv2.INTER_AREA)
    cv2.imwrite(img_path, img)
    
    
def upload_foto(img,id_):
    image_folder = os.path.join(id_,'image','')
    os.makedirs(image_folder)
    image_pose = os.path.join(id_,'pose')
    os.makedirs(image_pose)
    image_parse = os.path.join(id_,'image-parse')
    os.makedirs(image_parse)
    cloth_folder = os.path.join(id_,'cloth')
    os.makedirs(cloth_folder)
    cloth_mask_folder = os.path.join(id_,'cloth-mask')
    os.makedirs(cloth_mask_folder)
    image_path = os.path.join(image_folder, id_) + '.jpg'
    val_path = id_+"/val.txt"
    cv2.imwrite(image_path, img)
    resize(image_path)
    file = open(val_path, "w") 
    file.write(id_+'.jpg') 
    file.close() 
    #rodar !python3 run.py --model=mobilenet_thin --resize=432x368 --image=image_path
    os.system("python3 tf-pose-estimation/run.py --model=mobilenet_thin --resize=432x368 --image={}".format(image_path))



    #!python3 evaluate_pose_JPPNet-s2.py image_folder val_path id_+"/output/pose/val"
    print("python3 LIP_JPPNet/evaluate_pose_JPPNet-s2.py {} {} {}".format(image_folder,val_path,id_+"/output/pose/val"))
    os.system("python3 LIP_JPPNet/evaluate_pose_JPPNet-s2.py {} {} {}".format(image_folder,val_path,id_+"/output/pose/val"))

    print("python3 LIP_JPPNet/evaluate_parsing_JPPNet-s2.py {} {} {}".format(image_folder,val_path,id_+"/output/parsing/val"))
    os.system("python3 LIP_JPPNet/evaluate_parsing_JPPNet-s2.py {} {} {}".format(image_folder,val_path,id_+"/output/parsing/val"))

    #mover path_para_pasta_temp/output/parsing/val/nome_image_vis.png para path_para_pasta_temp/image-parse/nome_image.png
    copyfile(id_+"/output/parsing/val/"+id_+"_vis.png", image_parse+'/'+id_ + ".png")

    #!python3 dataset_neck_skin_correction.py id_
    print("python3 LIP_JPPNet/evaluate_parsing_JPPNet-s2.py {}".format(id_))
    os.system("python3 cp-vton-plus/dataset_neck_skin_correction.py {} ".format(id_))
    
    
def select_skin(id_pessoa, id_roupa):
    id_ = id_pessoa
    path_images_gustavo = 'root/parfait/parfait-training-recommendation'
    cloth_folder = os.path.join(id_,'cloth')
    cloth_mask_folder = os.path.join(id_,'cloth-mask')
    test_pairs = os.path.join(id_, 'test_pairs.txt')
    roupa_path = os.path.join(path_images_gustavo,'tumbs',id_roupa+".png")
    mask_path = os.path.join(path_images_gustavo,'tumbMasks',id_roupa+".png")
    cloth_file = os.path.join(cloth_folder, id_ + '.png')
    mask_file = os.path.join(cloth_mask_folder, id_ + '.png')
    print(roupa_path, cloth_file)
    copyfile(roupa_path, cloth_file)
    print(mask_path, mask_file)
    copyfile(mask_path, mask_file)
    dim = (192,256)
    img = cv2.imread(cloth_file)
    img = cv2.resize(img, dim, interpolation = cv2.INTER_AREA)
    cv2.imwrite(cloth_file, img)

    img = cv2.imread(mask_file)
    img = cv2.resize(img, dim, interpolation = cv2.INTER_AREA)
    cv2.imwrite(mask_file, img)

    file = open(test_pairs, "w") 
    file.write(id_+'.jpg ' + id_+'.png') 
    file.close()
    
    os.system("python3 cp-vton-plus/test.py --name GMM --stage GMM --workers 4 --dataroot {} --data_list test_pairs.txt --checkpoint checkpoints/GMM/gmm_final.pth".format(id_pessoa))
    img_pessoa = cv2.imread(id_+"/image/"+id_+".jpg")
    img_roupa = cv2.imread(id_+"/warp-cloth/"+id_+".png")
    img_mask = cv2.imread(id_+"/warp-mask/"+id_+".png", 0)
    img_pessoa_mask = cv2.imread(id_+"/image-mask/"+id_+".png", 0)
    kernel = np.ones((3,3),np.uint8)
    img_mask = cv2.erode(img_mask,kernel,iterations = 1)
    img_pessoa[img_mask >= 50] = 0
    img_roupa[img_mask < 50] = 0
    img_pessoa = img_pessoa + img_roupa
    plt.imshow(img_pessoa,cmap='gray')
    cv2.imwrite("bla.png", img_pessoa)

