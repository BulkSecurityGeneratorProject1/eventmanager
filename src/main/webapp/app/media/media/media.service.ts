import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Media } from './media.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MediaService {

    private resourceUrl = SERVER_API_URL + 'api/media';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/media';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(media: Media): Observable<Media> {
        const copy = this.convert(media);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(media: Media): Observable<Media> {
        const copy = this.convert(media);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Media> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Media.
     */
    private convertItemFromServer(json: any): Media {
        const entity: Media = Object.assign(new Media(), json);
        entity.created = this.dateUtils
            .convertDateTimeFromServer(json.created);
        return entity;
    }

    /**
     * Convert a Media to a JSON which can be sent to the server.
     */
    private convert(media: Media): Media {
        const copy: Media = Object.assign({}, media);

        copy.created = this.dateUtils.toDate(media.created);
        return copy;
    }
}
