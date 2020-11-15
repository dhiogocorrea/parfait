using System;
using System.Collections.Generic;

namespace ParfaitFront.Models
{
    public class ProductModel
    {
        public string productId { get; set; }
        public string url { get; set; }
        public string title { get; set; }
        public string description { get; set; }
        public string skuId { get; set; }
        public float percentDiscount { get; set; }
        public string gender { get; set; }
        public string brand { get; set; }
        public float listPrice { get; set; }
        public List<ImageModel> images { get; set; }
    }

    public class ImageModel 
    {
        public string id { get; set; }
        public string smallImageUrl { get; set; }
        public string zoomImageUrl { get; set; }
        public string mediumImageUrl { get; set; }
        public string thumbImageUrl { get; set; }
        public string showcaseImageUrl { get; set; }
        public string largeImageUrl { get; set; }
        public bool target { get; set; }
    }
}
