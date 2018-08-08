using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.ServiceModel.Web;

namespace Book
{
    [ServiceContract]
    public interface IService1
    {
        [OperationContract]
        [WebGet(UriTemplate = "/Books", ResponseFormat = WebMessageFormat.Json)]
        List<int> GetBookIds();

        [OperationContract]
        [WebGet(UriTemplate = "/Search/{title}", ResponseFormat = WebMessageFormat.Json)]
        List<Book> FindBooks(string title);

        [OperationContract]
        [WebGet(UriTemplate = "/Book/{id}", ResponseFormat = WebMessageFormat.Json)]
        WCF_Book GetBook(string id);

        [OperationContract]
        [WebInvoke(UriTemplate = "/Update", Method = "POST",
        RequestFormat = WebMessageFormat.Json,
        ResponseFormat = WebMessageFormat.Json)]
        void Update(WCF_Book book);
    }
    [DataContract]
    public class WCF_Book
    {
        [DataMember]
        public int BookID;

        [DataMember]
        public string Title;

        [DataMember]
        public int CategoryID;

        [DataMember]
        public string ISBN;

        [DataMember]
        public int Stock;

        [DataMember]
        public decimal Price;

        [DataMember]
        public string Author;

        public WCF_Book(int bookID, string title, int categoryID, string isbn, int stock, decimal price, string author)
        {
            this.BookID = bookID;
            this.Title = title;
            this.CategoryID = categoryID;
            this.ISBN = isbn;
            this.Stock = stock;
            this.Price = price;
            this.Author = author;
        }
    }
}
