using System.Collections;
namespace CursoDeLinQ;

class Program
{
    static void Main(string[] args)
    {
        List <Medico> listaMedicos = new List <Medico> ();
        List <Procedimiento> listaProcedimientos = new List <Procedimiento> ();

        using (StreamReader dataMedicos = new StreamReader("dataMedicos.json"))
        {
            string json     = dataMedicos.ReadToEnd();
            listaMedicos    = System.Text.
                                        Json.JsonSerializer
                                        .Deserialize<List<Medico>> (
                                            json,
                                            new System.Text.Json.JsonSerializerOptions() 
                                            {
                                                PropertyNameCaseInsensitive = true,
                                            }
                                        )!;
        }

        using (StreamReader dataProcedimientos = new StreamReader("dataProcedimientos.json"))
        {
            string json = dataProcedimientos.ReadToEnd();
            listaProcedimientos = System.Text.
                                        Json.JsonSerializer
                                        .Deserialize<List<Procedimiento>>(
                                            json,
                                            new System.Text.Json.JsonSerializerOptions()
                                            {
                                                PropertyNameCaseInsensitive = true,
                                            }
                                        )!;
        }

        //Console.WriteLine("\t{0, -25} {1,-14}", "Nombre", "Identificacion");
        //foreach ( var medico in listaMedicos )
        //{
        //    Console.WriteLine("\t{0,-25} {1,-14}", medico.Nombre, medico.Identificacion );
        //}

        Lista.MostrarMedicos(listaMedicos);
        Lista.MostrarMedicos(listaProcedimientos);
    }
}