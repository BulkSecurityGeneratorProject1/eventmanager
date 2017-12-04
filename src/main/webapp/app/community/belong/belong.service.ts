import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Belong } from './belong.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BelongService {

    private resourceUrl = SERVER_API_URL + 'api/belongs';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/belongs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(belong: Belong): Observable<Belong> {
        const copy = this.convert(belong);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(belong: Belong): Observable<Belong> {
        const copy = this.convert(belong);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Belong> {
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
     * Convert a returned JSON object to Belong.
     */
    private convertItemFromServer(json: any): Belong {
        const entity: Belong = Object.assign(new Belong(), json);
        entity.created = this.dateUtils
            .convertDateTimeFromServer(json.created);
        return entity;
    }

    /**
     * Convert a Belong to a JSON which can be sent to the server.
     */
    private convert(belong: Belong): Belong {
        const copy: Belong = Object.assign({}, belong);

        copy.created = this.dateUtils.toDate(belong.created);
        return copy;
    }
}
